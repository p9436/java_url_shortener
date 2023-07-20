package org.shorty.utils;

import com.google.gson.Gson;

public class ObjectView {
    // Exclude object from export
    private transient Object object;

    public ObjectView(Object object) {
        this.object = object;
    }

    public String toJson() {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        return json;
    }
}
