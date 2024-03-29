package br.ufal.ic.p2.wepayu.models.StrategyDB;

import java.util.HashMap;
import java.util.ArrayList;

public interface CreateEntityDB {

    public <K, T> void connect(String file, HashMap<K, T> map) throws Exception;

    public void connect(String file, ArrayList<String> list) throws Exception;
}
