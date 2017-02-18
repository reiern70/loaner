package com.antilia.loan.common.domain;

import org.json.JSONException;
import org.json.JSONWriter;

public interface IJsonable {

    public static final  IJsonable EMPTY_OBJECT = new IJsonable() {
        public void toJSON(JSONWriter json) throws JSONException {
            json.object(); json.endObject();
        }
    };

    public static final  IJsonable EMPTY_ARRAY = new IJsonable() {
        public void toJSON(JSONWriter json) throws JSONException {
            json.array(); json.endArray();
        }
    };

    public void toJSON(JSONWriter json) throws JSONException;
}
