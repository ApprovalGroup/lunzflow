package com.lunzflow.platform.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.lunzflow.platform.listener.ProcessEndListener;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.FlowableMailProperties;
import org.flowable.spring.boot.FlowableProperties;
import org.flowable.spring.boot.ProcessEngineAutoConfiguration;
import org.flowable.spring.boot.app.FlowableAppProperties;
import org.flowable.spring.boot.idm.FlowableIdmProperties;
import org.flowable.spring.boot.process.FlowableProcessProperties;
import org.flowable.spring.boot.process.Process;
import org.flowable.spring.boot.process.ProcessAsync;
import org.flowable.spring.boot.process.ProcessAsyncHistory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableConfigurationProperties(FlowableProperties.class)
public class FlowDataSourceProcessEngineAutoConfiguration extends ProcessEngineAutoConfiguration {

    @Value("${flowable.diagram.activityFontName}")
    private String activityFontName;

    @Value("${flowable.diagram.labelFontName}")
    private String labelFontName;

    @Value("${flowable.diagram.annotationFontName}")
    private String annotationFontName;

    public FlowDataSourceProcessEngineAutoConfiguration(FlowableProperties flowableProperties,
                                                        FlowableProcessProperties processProperties, FlowableAppProperties appProperties, FlowableIdmProperties idmProperties,
                                                        FlowableMailProperties mailProperties) {
        super(flowableProperties, processProperties, appProperties, idmProperties, mailProperties);
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
                                                                             @Process ObjectProvider<IdGenerator> processIdGenerator,
                                                                             ObjectProvider<IdGenerator> globalIdGenerator,
                                                                             @ProcessAsync ObjectProvider<AsyncExecutor> asyncExecutorProvider,
                                                                             @ProcessAsyncHistory ObjectProvider<AsyncExecutor> asyncHistoryExecutorProvider) throws IOException {

        SpringProcessEngineConfiguration conf = super.springProcessEngineConfiguration(dataSource, platformTransactionManager, processIdGenerator, globalIdGenerator, asyncExecutorProvider, asyncHistoryExecutorProvider);
        //conf.setIdmEngineConfigurator(idmEngineConfigurator(dataSource));
        conf.setActivityFontName(activityFontName);
        conf.setLabelFontName(labelFontName);
        conf.setAnnotationFontName(annotationFontName);
        conf.setTypedEventListeners(getGlobalFlowableEventListener());
        return conf;
    }

//    @Bean
//    public IdmEngineConfigurator idmEngineConfigurator(DataSource dataSource) {
//        IdmEngineConfiguration idmEngineConfiguration = new IdmEngineConfiguration();
//        idmEngineConfiguration.setDataSource(dataSource);
//		      自定义扩展用户信息
//        idmEngineConfiguration.setIdmIdentityService(new CustomsIdentityServiceImpl());
//
//        IdmEngineConfigurator idmEngineConfigurator = new IdmEngineConfigurator();
//        idmEngineConfigurator.setIdmEngineConfiguration(idmEngineConfiguration);
//        return idmEngineConfigurator;
//    }
    /**
     * 设置系统级别监听器
     *
     * @return
     */
    private Map<String, List<FlowableEventListener>> getGlobalFlowableEventListener() {
        Map<String, List<FlowableEventListener>> typedListeners = new HashMap<String, List<FlowableEventListener>>();

        List<FlowableEventListener> processCompleteList = new ArrayList<FlowableEventListener>();
        processCompleteList.add(new ProcessEndListener());
        typedListeners.put("PROCESS_STARTED", processCompleteList);

        return typedListeners;

    }
}
