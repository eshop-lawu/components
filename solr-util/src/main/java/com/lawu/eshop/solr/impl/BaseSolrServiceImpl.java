package com.lawu.eshop.solr.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrCallback;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;

import com.lawu.eshop.solr.service.BaseSolrService;
import com.lawu.eshop.solr.utils.AopTargetUtils;

/**
 * 顶级solr服务实现类
 * 
 * @author jiangxinjun
 * @createDate 2018年1月26日
 * @updateDate 2018年1月26日
 */
public class BaseSolrServiceImpl<T, ID extends Serializable> implements BaseSolrService<T, ID> {

    private SimpleSolrRepository<T, ID> simpleSolrRepository;

    @SuppressWarnings("unchecked")
    protected void init(SolrCrudRepository<T, ID> repository) {
        simpleSolrRepository = (SimpleSolrRepository<T, ID>) AopTargetUtils.getTarget(repository);
    }

    protected final SimpleSolrRepository<T, ID> getSimpleSolrRepository() {
        return simpleSolrRepository;
    }

    protected final SolrTemplate getSolrOperations() {
        return (SolrTemplate) simpleSolrRepository.getSolrOperations();
    }
    
    protected final Page<T> query(SolrQuery solrQuery) {
        return query(solrQuery, METHOD.GET);
    }
    
    protected final Page<T> query(SolrQuery solrQuery, METHOD method) {
        QueryResponse queryResponse = getSolrOperations().execute(new SolrCallback<QueryResponse>() {
            @Override
            public QueryResponse doInSolr(SolrClient solrServer) throws SolrServerException, IOException {
                return solrServer.query(solrQuery, method);
            }
        });
        List<T> beans = getSolrOperations().convertQueryResponseToBeans(queryResponse, getSimpleSolrRepository().getEntityClass());
        SolrDocumentList results = queryResponse.getResults();
        long numFound = results == null ? 0 : results.getNumFound();
        Float maxScore = results == null ? null : results.getMaxScore();
        Pageable pageable = null;
        if (solrQuery.getStart() != null && solrQuery.getRows() != null) {
            pageable = new SolrPageRequest(solrQuery.getStart(), solrQuery.getRows());
        } else {
            pageable = new SolrPageRequest(0, 10);
        }
        SolrResultPage<T> page = new SolrResultPage<T>(beans, pageable, numFound, maxScore);
        return page;
    }
}
