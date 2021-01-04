/*
 * Created on 28.09.2006
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: BPrevalentDatabase.java,v 1.2 2016/04/15 10:37:29 dauren_home Exp $
 */
package com.realsoft.commons.beans.persist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.realsoft.commons.beans.CommonsBeansConstants;

public class BPrevalentDatabase implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BPrevalentDatabase.class);

	private String name = null;

	private Map<String, BPrevalentSchema> schemas = new LinkedHashMap<String, BPrevalentSchema>();

	public BPrevalentDatabase(String name) {
		super();
		this.name = name;
	}

	public BPrevalentSchema getSchema(String name) throws BPersistException {
		if (!schemas.containsKey(name))
			throw new BPersistException(CommonsBeansConstants.ERR_SYSTEM,
					"commons-beans.store.getting-schema.schema-abcent.error",
					"Such schema " + name + " abcent");
		return schemas.get(name);
	}

	public BPrevalentSchema createSchema(String name) throws BPersistException {
		log.debug("is schema " + name + " exists " + schemas.containsKey(name));
		if (!schemas.containsKey(name)) {
			BPrevalentSchema schema = new BPrevalentSchema(name);
			schemas.put(name, schema);
			return schema;
		} else {
			return schemas.get(name);
		}
	}

	public List<String> getSchemaList() {
		List<String> schemaList = new ArrayList<String>();
		for (BPrevalentSchema schema : schemas.values()) {
			schemaList.add(schema.getName());
		}
		return schemaList;
	}

	public void removeSchema(String name) {
		schemas.remove(name);
	}
}
