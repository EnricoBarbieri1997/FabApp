package com.simonegherardi.enricobarbieri.fabapp.restapi;

/**
 * Created by Xxenr on 16/06/2018.
 */

public class DummyRestable implements IRESTable{

    @Override
    public void Success(RESTResponse response) {
        JSON a = new JSON(response.GetResponse());
        try {
            JSON c = new JSON("");
            JSON d = new JSON("{}");
            d.Set("simo", "bello");
            //c.Set("aaa", "bbb");
            JSON b = (JSON)a.GetParser("1");
            b.GetInt("id");
            b.GetString("id");
            b.Set("prova", "ciao");
            b.Set("numero", 1);
            b.Update("numero","3");
            a.Set("2", b);
        } catch (JSONParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    @Override
    public void Error(RESTResponse result) {

    }
}
