package com.main.getOpenData;


import com.main.getOpenData.DAO.Bilding;
import com.main.getOpenData.DAO.BildingDao;

public class WorkWithBilding {

    private BildingDao bildingDao;

    public WorkWithBilding(BildingDao bildingDao) {
        this.bildingDao = bildingDao;
    }

    public Bilding getOrWriteBilding(double lon, double lat){
        Bilding resultBilding = null;
        for (Bilding bilding : bildingDao.findAll()) {
            if (bilding.getLatitude() == lat & bilding.getLongitude() == lon){
                resultBilding = bilding;
                break;
            }
        }
        if(resultBilding == null ) {
           resultBilding = bildingDao.save(new Bilding(lon, lat));
        }
        return resultBilding;
    }
}
