/**
 * JsonSchema2PojoTest.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年7月11日 	 
 */
public class JsonSchema2PojoTest {

	public static void main(String[] args) throws Exception { 

		JCodeModel codeModel = new JCodeModel();

		URL source = new URL("http://files.developer.sabre.com/doc/providerdoc/STPS/bfm/v310/OTA_AirLowFareSearchRS.jsonschema");

		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}
		};

		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, "ClassName", "com.example", source);

		codeModel.build(new File("output"));

	}

}
