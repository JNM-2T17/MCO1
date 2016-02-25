package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import model.AvgDeathAge;
import model.AvgOFWsPerNuclearFamily;
import model.CatchRatio;
import model.CommonBeneficiary;
import model.CropVolume;
import model.FishCount;
import model.HealthyKids;

public class BaseQueries {
	public static Collection<AvgOFWsPerNuclearFamily> getAvgOFWsPerNuclearFamilyWithOFWCountGreaterThan(int val){
		ArrayList<AvgOFWsPerNuclearFamily> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("SELECT mun, zone, brgy, purok"
							+ ", SUM(nnucfam) AS `Nuclear Families`, SUM(nofw) AS OFWs"
							+ ", SUM(nofw) / SUM(nnucfam) AS `Average OFW's per Nuclear Family`"
							+ " FROM db_hpq.hpq_hh"
							+ " GROUP BY mun, zone, brgy, purok"
							+ " HAVING SUM(nofw) > "+val);
			
			while(resultSet.next())
			{
				AvgOFWsPerNuclearFamily res = new AvgOFWsPerNuclearFamily(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getString(3), 
						resultSet.getString(4), 
						resultSet.getInt(5), 
						resultSet.getInt(6), 
						resultSet.getDouble(7));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Collection<HealthyKids> getPlacesWithHealthyKidsGreaterThan(int val){
		ArrayList<HealthyKids> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("SELECT country_resid, prov_resid_code, mnutind,COUNT(mnutind) nutCount"
							+ " FROM hpq_mem"
							+ " WHERE mnutind <= 2"
							+ " GROUP BY country_resid, prov_resid_code,mnutind"
							+ " HAVING nutCount > "+val);
			
			while(resultSet.next())
			{
				HealthyKids res = new HealthyKids(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getInt(3), 
						resultSet.getInt(4));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Collection<AvgDeathAge> getAvgDeathAgeGraterThan(int val){
		ArrayList<AvgDeathAge> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("SELECT H.mun,H.zone,H.brgy, mdeadsx, AVG(mdeadage) avg_death_age"
							+ " FROM hpq_hh H, hpq_death D"
							+ " WHERE H.id = D.hpq_hh_id"
							+ " GROUP BY H.mun,H.zone,H.brgy,mdeadsx"
							+ " HAVING AVG(mdeadage) > "+val);
			
			while(resultSet.next())
			{
				AvgDeathAge res = new AvgDeathAge(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getString(3), 
						resultSet.getInt(4), 
						resultSet.getDouble(5));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Collection<FishCount> getFishCountsGraterThan(int val){
		ArrayList<FishCount> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("SELECT H.mun,H.zone,H.brgy, aquanitype, COUNT(H.id) fishcount"
							+ " FROM hpq_hh H, hpq_aquani A"
							+ " WHERE H.id = A.hpq_hh_id AND aquanitype = 6"
							+ " GROUP BY H.mun,H.zone,H.brgy,aquanitype"
							+ " HAVING COUNT(H.id) > "+val);
			
			while(resultSet.next())
			{
				FishCount res = new FishCount(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getString(3), 
						resultSet.getInt(4), 
						resultSet.getInt(5));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Collection<CropVolume> getCropVolumesGraterThan(double val){
		ArrayList<CropVolume> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("SELECT H.mun,H.zone,H.brgy, SUM(crop_vol) AS totalcrop, SUM(alp_area) AS totalArea, SUM(crop_vol)/SUM(alp_area) AS cropDensity"
							+ " FROM hpq_hh H, hpq_alp A, hpq_crop C"
							+ " WHERE H.id = A.hpq_hh_id AND H.id = C.hpq_hh_id"
							+ " GROUP BY H.mun,H.zone,H.brgy"
							+ " HAVING cropDensity > "+val);
			
			while(resultSet.next())
			{
				CropVolume res = new CropVolume(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getString(3), 
						resultSet.getInt(4), 
						resultSet.getInt(5), 
						resultSet.getDouble(6));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Collection<CatchRatio> getCatchRatiosGraterThan(double val){
		ArrayList<CatchRatio> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("select mun, zone, brgy, SUM(aquaequip_line) AS totalequip, SUM(aquani_vol) AS totalvol, SUM(aquani_vol)/SUM(aquaequip_line) AS CatchPerEquip "
							+ " from hpq_aquaequip aa, hpq_aquani ap, hpq_hh h"
							+ " where h.id = aa.hpq_hh_id && h.id = ap.hpq_hh_id"
							+ " group by H.mun,H.zone,H.brgy"
							+ " HAVING CatchPerEquip > "+val);
			
			while(resultSet.next())
			{
				CatchRatio res = new CatchRatio(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getString(3), 
						resultSet.getLong(4), 
						resultSet.getLong(5), 
						resultSet.getDouble(6));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static Collection<CommonBeneficiary> getCommonBeneficiariesGraterThan(int val){
		ArrayList<CommonBeneficiary> list = new ArrayList<>();
		Connection connection = DBManager.getInstance().getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery
					("SELECT H.mun,H.zone,H.brgy,COUNT(H.id) benefCount"
							+ " FROM hpq_hh H, hpq_phiheal_spon_mem PSM, hpq_phiheal_empl_mem PEM"
							+ " ,hpq_phiheal_indiv_mem PIM, hpq_phiheal_life_mem PLM"
							+ " WHERE H.id = PSM.hpq_hh_id AND H.id = PEM.hpq_hh_id AND H.id = PIM.hpq_hh_id "
							+ " AND H.id = PLM.hpq_hh_id"
							+ " AND PSM.phiheal_spon_mem_refno = PEM.phiheal_empl_mem_refno"
							+ " AND PEM.phiheal_empl_mem_refno = PIM.phiheal_indiv_mem_refno "
							+ " AND PIM.phiheal_indiv_mem_refno = PLM.phiheal_life_mem_refno "
							+ " GROUP BY H.mun,H.zone,H.brgy"
							+ " HAVING benefCount > "+val);
			
			while(resultSet.next())
			{
				CommonBeneficiary res = new CommonBeneficiary(
						resultSet.getString(1), 
						resultSet.getString(2), 
						resultSet.getString(3), 
						resultSet.getInt(4));
				list.add(res);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}