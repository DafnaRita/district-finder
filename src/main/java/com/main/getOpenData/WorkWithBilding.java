package com.main.getOpenData;


import com.main.getOpenData.DAO.Bilding;
import com.main.getOpenData.DAO.BildingDao;

public class WorkWithBilding {

    private BildingDao bildingDao;

    public WorkWithBilding(BildingDao bildingDao) {
        this.bildingDao = bildingDao;
    }

    public long getOrWriteBilding(double lon, double lat){
        long id_bilding = 0;
        for (Bilding bilding : bildingDao.findAll()) {
            if (bilding.getLatitude() == lat & bilding.getLongitude() == lon){
                id_bilding = bilding.getId();
                break;
            }
        }
        if(id_bilding == 0) {
            Bilding bilding = bildingDao.save(new Bilding(lon, lat));
            id_bilding = bilding.getId();
        }
        return id_bilding;
    }
}
