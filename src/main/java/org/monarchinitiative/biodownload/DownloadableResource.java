package org.monarchinitiative.biodownload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Class used to store a downloadable resource and with all the resources objects that can be downloaded.
 */
public class DownloadableResource {

    private static final Logger logger = LoggerFactory.getLogger(DownloadableResource.class);

    private final String name;
    private final URL url;

    public final static DownloadableResource GO_JSON = new DownloadableResource("go.json", createURL("http://purl.obolibrary.org/obo/go.json"));
    public final static DownloadableResource GO_OBO = new DownloadableResource("go.obo", createURL("http://purl.obolibrary.org/obo/go.obo"));
    public final static DownloadableResource MEDGENE_2MIM = new DownloadableResource("mim2gene_medgen", createURL("ftp://ftp.ncbi.nlm.nih.gov/gene/DATA/mim2gene_medgen"));
    public final static DownloadableResource PROSITE = new DownloadableResource("prosite.dat", createURL("ftp://ftp.expasy.org/databases/prosite/prosite.dat"));
    public final static DownloadableResource HGNC = new DownloadableResource("hgnc_complete_set.txt", createURL("ftp://ftp.ebi.ac.uk/pub/databases/genenames/hgnc/tsv/hgnc_complete_set.txt"));
    public final static DownloadableResource GO_GAF = new DownloadableResource("goa_human.gaf", createURL("http://geneontology.org/gene-associations/goa_human.gaf"));
    public final static DownloadableResource GO_GAFGZ = new DownloadableResource("goa_human.gaf.gz", createURL("http://geneontology.org/gene-associations/goa_human.gaf.gz"));
    public final static DownloadableResource MONDO_JSON = new DownloadableResource("mondo.json", createURL("http://purl.obolibrary.org/mondo/mondo.json"));
    public final static DownloadableResource MONDO_OWL = new DownloadableResource("mondo.owl", createURL("http://purl.obolibrary.org/mondo/mondo.owl"));
    public final static DownloadableResource ECTO_JSON = new DownloadableResource("ecto.json", createURL("https://raw.githubusercontent.com/EnvironmentOntology/environmental-exposure-ontology/master/ecto.json"));
    public final static DownloadableResource ECTO_OWL = new DownloadableResource("ecto.owl", createURL("https://raw.githubusercontent.com/EnvironmentOntology/environmental-exposure-ontology/master/ecto.owl"));
    public final static DownloadableResource MAXO_JSON = new DownloadableResource("maxo.json", createURL("https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.json"));
    public final static DownloadableResource MAXO_OWL = new DownloadableResource("maxo.owl", createURL("https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.owl"));
    public final static DownloadableResource MAXO_OBO = new DownloadableResource("maxo.obo", createURL("https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.obo"));
    public final static DownloadableResource HP_JSON = new DownloadableResource("hp.json", createURL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.json"));
    public final static DownloadableResource HP_OBO = new DownloadableResource("hp.obo", createURL("https://raw.githubusercontent.com/obophenotype/human-phenotype-ontology/master/hp.obo"));
    public final static DownloadableResource PHENOTYPE_HP = new DownloadableResource("phenotype.hpoa", createURL("http://purl.obolibrary.org/obo/hp/hpoa/phenotype.hpoa"));
    public final static DownloadableResource GENE_INFO = new DownloadableResource("Homo_sapiens_gene_info.gz", createURL("ftp://ftp.ncbi.nih.gov/gene/DATA/GENE_INFO/Mammalia/Homo_sapiens.gene_info.gz"));

    /**
     * Constructor
     *
     * @param name Name of file to download to
     * @param url  {@link URL} of source file
     */
    public DownloadableResource(String name, URL url) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
        this.url = Objects.requireNonNull(url, "Url must not be null");
    }

    /**
     * Create a URL and return null if {@link MalformedURLException} occurs
     *
     * @param urlStr URL string
     * @return a {@link URL} or null if malformed
     */
    private static URL createURL(String urlStr) {
        try {
            return createURLExc(urlStr);
        } catch (MalformedURLException mue) {
            logger.error("Malformed URL for {}", urlStr);
        }
        return null;
    }

    /**
     * Create a URL and throw {@link MalformedURLException} if error occurs
     *
     * @param urlStr URL string
     * @return a {@link URL}
     * @throws MalformedURLException exception
     */
    private static URL createURLExc(String urlStr) throws MalformedURLException {
        URL url = new URL(urlStr);
        logger.debug("Created url from {}: {}", urlStr, url);
        return url;
    }

    public String getName() {
        return name;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DownloadableResource that = (DownloadableResource) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "DownloadableResource{" +
                "name='" + name + '\'' +
                ", url=" + url.toString() +
                '}';
    }
}
