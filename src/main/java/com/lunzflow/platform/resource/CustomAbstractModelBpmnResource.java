package com.lunzflow.platform.resource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.lunzflow.platform.service.impl.CustomModelServiceImpl;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.service.exception.BaseModelerRestException;
import org.flowable.ui.common.service.exception.InternalServerErrorException;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.rest.app.AbstractModelBpmnResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jbarrez
 */
@RestController
@RequestMapping("/custom")
public class CustomAbstractModelBpmnResource extends AbstractModelBpmnResource{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAbstractModelBpmnResource.class);
    @Autowired
    protected CustomModelServiceImpl customModelServiceImpl;
    
    /**
     * GET /rest/models/{modelId}/bpmn20 -> Get BPMN 2.0 xml
     */
    @RequestMapping(value = "/rest/models/{processModelId}/bpmn20", method = RequestMethod.GET)
    public void getProcessModelBpmn20Xml(HttpServletResponse response, @PathVariable String processModelId) throws IOException {
    	 if (processModelId == null) {
             throw new BadRequestException("No process model id provided");
         }

         Model model = customModelServiceImpl.getModel(processModelId);
         generateBpmn20Xml(response, model);
    }
    protected void generateBpmn20Xml(HttpServletResponse response, AbstractModel model) {
        String name = model.getName().replaceAll(" ", "_") + ".bpmn20.xml";
        String encodedName = null;
        try {
            encodedName = "UTF-8''" + URLEncoder.encode(name, "UTF-8");
        } catch (Exception e) {
            LOGGER.warn("Failed to encode name " + name);
        }
        
        String contentDispositionValue = "attachment; filename=" + name;
        if (encodedName != null) {
            contentDispositionValue += "; filename*=" + encodedName;
        }
        
        response.setHeader("Content-Disposition", contentDispositionValue);
        if (model.getModelEditorJson() != null) {
            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();
                response.setContentType("application/xml");

                BpmnModel bpmnModel = customModelServiceImpl.getBpmnModel(model);
                byte[] xmlBytes = customModelServiceImpl.getBpmnXML(bpmnModel);
                BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(xmlBytes));

                byte[] buffer = new byte[8096];
                while (true) {
                    int count = in.read(buffer);
                    if (count == -1) {
                        break;
                    }
                    servletOutputStream.write(buffer, 0, count);
                }

                // Flush and close stream
                servletOutputStream.flush();
                servletOutputStream.close();

            } catch (BaseModelerRestException e) {
                throw e;

            } catch (Exception e) {
                LOGGER.error("Could not generate BPMN 2.0 XML", e);
                throw new InternalServerErrorException("Could not generate BPMN 2.0 xml");
            }
        }
    }

}
