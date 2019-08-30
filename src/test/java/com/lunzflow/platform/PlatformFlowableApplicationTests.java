package com.lunzflow.platform;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=PlatformFlowableApplicationTests.class)
public class PlatformFlowableApplicationTests {
	@Autowired
	private RepositoryService repositoryService;
	
	/**
     * classpath方式部署
     * 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
     */
    @Test
    public void deploy(){
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("customfield")
                .key("customfield")
                .name("自定义属性")
                .addClasspathResource("自定义属性.bpmn20.xml");
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("自定义属性,流程ID: " + deploy.getId());
    }

}

