import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class YY {
    @Test
    public void show() throws Exception{
        SolrServer solrServer =new HttpSolrServer("http://192.168.25.133:8080/solr");
        SolrInputDocument document =new SolrInputDocument();
        document.addField("id","test1");
        document.addField("item_title","sksjakj");

        solrServer.add(document);
        solrServer.commit();

    }
}
