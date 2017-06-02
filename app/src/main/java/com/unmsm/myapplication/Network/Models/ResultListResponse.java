package com.unmsm.myapplication.Network.Models;

import java.util.List;

/**
 * Created by rubymobile on 02/06/17.
 */

public class ResultListResponse {
    List<ResultResponse> results;

    public List<ResultResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultResponse> results) {
        this.results = results;
    }
}
