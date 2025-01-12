package org.monarchinitiative.biodownload.ftp;

import org.monarchinitiative.biodownload.FileDownloadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 * Java NIO FTP download
 */
public final class NIOftp {

    private static final Logger logger = LoggerFactory.getLogger(NIOftp.class);

    private static final int FTP_BUFFER_CAPACITY = 2048;

    private NIOftp() {
        throw new IllegalStateException("This is not an instantiable class.");
    }

    /**
     * Download a file from a given ftp URL
     *
     * @param from   ftp url to download from
     * @param target Full path to target file to download to
     * @return file downloaded
     * @throws FileDownloadException
     */
    public static File ftp(final URL from, File target) throws FileDownloadException {
        File downloadedFile;
        if (Objects.isNull(from)) {
            throw new FileDownloadException("URL required for ftp source");
        }

        if (target == null) {
            throw new FileDownloadException("target required");
        }

        final FileReader reader = createReader(from);
        final FileWriter writer = createWriter(target);

        try {
            logger.info("Starting ftp download from {}", from);

            ByteBuffer buffer = ByteBuffer.allocate(FTP_BUFFER_CAPACITY);
            while (reader.read(buffer) >= 0) {
                writer.write(buffer.flip());

                buffer.clear();
            }
        } finally {
            logger.info("Transfer completed.");
            reader.close();
            writer.close();
            downloadedFile = target;
        }
        return downloadedFile;
    }

    private static FileReader createReader(final URL from) throws FileDownloadException {
        assert !Objects.isNull(from);
        return new FileReader(from);
    }

    private static FileWriter createWriter(File target) throws FileDownloadException {
        assert target != null;
        return new FileWriter(target);
    }
}

final class FileReader {

    private final ReadableByteChannel from;

    FileReader(final URL url) throws FileDownloadException {
        try {
            from = Channels.newChannel(url.openStream());
        } catch (IOException e) {
            throw new FileDownloadException("ERROR: problem connecting when downloading file.", e);
        }
    }

    int read(final ByteBuffer buffer) throws FileDownloadException {
        try {
            return from.read(buffer);
        } catch (IOException e) {
            throw new FileDownloadException("ERROR: problem connecting when downloading file.", e);
        }
    }

    void close() throws FileDownloadException {
        try {
            Objects.requireNonNull(from).close();
        } catch (IOException e) {
            throw new FileDownloadException("ERROR: problem connecting when downloading file.", e);
        }
    }
}

final class FileWriter {

    private final FileChannel target;

    FileWriter(File file) throws FileDownloadException {
        try {
            target = FileChannel.open(file.toPath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new FileDownloadException("ERROR: problem connecting when downloading file.", e);
        }
    }

    void write(final ByteBuffer buffer) throws FileDownloadException {
        try {
            target.write(buffer);
            while (buffer.hasRemaining()) {
                buffer.compact();
                target.write(buffer);
            }
        } catch (IOException e) {
            throw new FileDownloadException("ERROR: problem connecting when downloading file.", e);
        }
    }

    void close() throws FileDownloadException {
        try {
            target.close();
        } catch (IOException e) {
            throw new FileDownloadException("ERROR: problem connecting when downloading file.", e);
        }
    }
}
