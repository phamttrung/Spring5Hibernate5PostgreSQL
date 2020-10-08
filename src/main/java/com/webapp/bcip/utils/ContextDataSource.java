/*******************************************************************************
 * Copyright 2016 4DM Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *******************************************************************************
 * Purpose: get datasource from spring context in WEB-INF
 * 
 *******************************************************************************/
package com.webapp.bcip.utils;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author 4DM Inc
 */
public class ContextDataSource {
    
    private static final Logger logger = Logger.getLogger(ContextDataSource.class.getName());
    
    // get data source from application context
    public DataSource getDataSource(){

        // open/read the applicliation context file
            // belove instruction to ger absolute path of current java apps
        //URL location = NaicsUtility.class.getProtectionDomain().getCodeSource().getLocation();
        //System.out.println(location);
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("./../db.xml");
        DataSource dataSource = null;
        try{            
            dataSource = (DataSource) appContext.getBean("dataSource");
        } catch ( Exception ex ) {
            logger.log( Level.SEVERE, "ERROR: can't create spring context and data source from application context", ex.getMessage() );
        } finally {
            appContext.close();
        }
        return dataSource;
    }
    
}
