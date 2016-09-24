package com.day.l.video.video.entity;

/**
 * ��Ƶ����  listview ������ԭ��
 * @author cyl
 *
 * 2015��11��19��
 */

public class SortVideoAdapterDao {
	private VideoListDataEntity.ListBean firstAppInfoDao;
	private VideoListDataEntity.ListBean secondAppInfoDao;
	private VideoListDataEntity.ListBean thirdAppInfoDao;
	
	
	
	public SortVideoAdapterDao() {
		super();
	}
	public SortVideoAdapterDao(VideoListDataEntity.ListBean firstAppInfoDao,
							   VideoListDataEntity.ListBean secondAppInfoDao, VideoListDataEntity.ListBean thirdAppInfoDao) {
		super();
		this.firstAppInfoDao = firstAppInfoDao;
		this.secondAppInfoDao = secondAppInfoDao;
		this.thirdAppInfoDao = thirdAppInfoDao;
	}
	public VideoListDataEntity.ListBean getFirstAppInfoDao() {
		return firstAppInfoDao;
	}
	public void setFirstAppInfoDao(VideoListDataEntity.ListBean firstAppInfoDao) {
		this.firstAppInfoDao = firstAppInfoDao;
	}
	public VideoListDataEntity.ListBean getSecondAppInfoDao() {
		return secondAppInfoDao;
	}
	public void setSecondAppInfoDao(VideoListDataEntity.ListBean secondAppInfoDao) {
		this.secondAppInfoDao = secondAppInfoDao;
	}
	public VideoListDataEntity.ListBean getThirdAppInfoDao() {
		return thirdAppInfoDao;
	}
	public void setThirdAppInfoDao(VideoListDataEntity.ListBean thirdAppInfoDao) {
		this.thirdAppInfoDao = thirdAppInfoDao;
	}
	

}
