package com.cn.poker.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.poker.common.entity.Page;
import com.cn.poker.common.entity.Query;
import com.cn.poker.common.entity.R;
import com.cn.poker.common.util.CommonUtils;
import com.cn.poker.dao.WpStrateSingleSumMapper;
import com.cn.poker.entity.*;
import com.cn.poker.manager.WpStrateManager;
import com.cn.poker.manager.WpStratePackSumManager;
import com.cn.poker.manager.WpStrategyDetailManager;
import com.cn.poker.service.WpStrateService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 策略包
 * @author <>
 */
@Service("wpStrateService")
public class WpStrateServiceImpl implements WpStrateService {

	@Autowired
	private WpStrateManager wpStrateManager;

	@Autowired
    private WpStrategyDetailManager wpStrategyDetailManager;

	@Autowired
	private WpStratePackSumManager wpStratePackSumManager;

	@Autowired
	private WpStrateSingleSumMapper wpStrateSingleSumMapper;

    /**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<WpStrateEntity> listWpStrate(Map<String, Object> params) {
		Query query = new Query(params);
		Page<WpStrateEntity> page = new Page<>(query);
		wpStrateManager.listWpStrate(page, query);
        WpStrategyDetailEntity wpStrategyDetailEntity = null;
        WpStrategyDetailEntity wpStrategyDetailEntityTemp = null;
        wpStrategyDetailEntity = new WpStrategyDetailEntity();
        wpStrategyDetailEntity.setUserId(Integer.parseInt(params.get("userId").toString()));
        wpStrategyDetailEntity = wpStrategyDetailManager.selectOne(wpStrategyDetailEntity);
        List<WpStrateEntity> rows = page.getRows();
        for (WpStrateEntity row : rows) {
           /* wpStrategyDetailEntity = new WpStrategyDetailEntity();
            wpStrategyDetailEntity.setPoolType(4);  //4 全部策略包
            wpStrategyDetailEntity.setType(Integer.parseInt(params.get("type").toString()));
            wpStrategyDetailEntity.setUserId(Integer.parseInt(params.get("userId").toString()));
            wpStrategyDetailEntity = wpStrategyDetailManager.selectOne(wpStrategyDetailEntity);*/
            if(wpStrategyDetailEntity!=null && wpStrategyDetailEntity.getPoolType()==4 && wpStrategyDetailEntity.getType()==Integer.parseInt(params.get("type").toString())){
                wpStrategyDetailEntityTemp = wpStrategyDetailEntity;
            }

			if (wpStrategyDetailEntityTemp==null) {
			/*	wpStrategyDetailEntity = new WpStrategyDetailEntity();
				wpStrategyDetailEntity.setPoolType(Integer.parseInt(params.get("poolType").toString()));
				wpStrategyDetailEntity.setType(Integer.parseInt(params.get("type").toString()));
				wpStrategyDetailEntity.setUserId(Integer.parseInt(params.get("userId").toString()));
//                wpStrategyDetailEntity.setStrategyId(row.getId());
				wpStrategyDetailEntity = wpStrategyDetailManager.selectOne(wpStrategyDetailEntity);*/
                if(wpStrategyDetailEntity!=null && wpStrategyDetailEntity.getPoolType()==Integer.parseInt(params.get("poolType").toString()) && wpStrategyDetailEntity.getType()==Integer.parseInt(params.get("type").toString())){
                    wpStrategyDetailEntityTemp = wpStrategyDetailEntity;
                }
			}


            if (wpStrategyDetailEntityTemp!=null) {
                if(wpStrategyDetailEntityTemp.getStrategyId()==null ){
                    row.setStartTime(wpStrategyDetailEntityTemp.getStartDate());
                    row.setEndTime(wpStrategyDetailEntityTemp.getEndDate());
                    row.setDayCount(wpStrategyDetailEntityTemp.getDayCount());
                }else {
                    if(wpStrategyDetailEntityTemp.getStrategyId()==row.getId()){
                        row.setStartTime(wpStrategyDetailEntityTemp.getStartDate());
                        row.setEndTime(wpStrategyDetailEntityTemp.getEndDate());
                        row.setDayCount(wpStrategyDetailEntityTemp.getDayCount());
                    }
                }

            }
           /* else {
                wpStrategyDetailEntity = new WpStrategyDetailEntity();
                wpStrategyDetailEntity.setPoolType(Integer.parseInt(params.get("poolType").toString()));
                wpStrategyDetailEntity.setType(Integer.parseInt(params.get("type").toString()));
                wpStrategyDetailEntity.setUserId(Integer.parseInt(params.get("userId").toString()));
//                wpStrategyDetailEntity.setStrategyId(row.getId());
                wpStrategyDetailEntity = wpStrategyDetailManager.selectOne(wpStrategyDetailEntity);
                if (wpStrategyDetailEntity!=null) {
                    if(wpStrategyDetailEntity.getStrategyId()==null ){
                        row.setStartTime(wpStrategyDetailEntity.getStartDate());
                        row.setEndTime(wpStrategyDetailEntity.getEndDate());
                        row.setDayCount(wpStrategyDetailEntity.getDayCount());
                    }else {
                        if(wpStrategyDetailEntity.getStrategyId()==row.getId()){
                            row.setStartTime(wpStrategyDetailEntity.getStartDate());
                            row.setEndTime(wpStrategyDetailEntity.getEndDate());
                            row.setDayCount(wpStrategyDetailEntity.getDayCount());
                        }
                    }

                }
            }*/
        }
        page.setRows(rows);
		return page;
	}

	/**
     * 分页查询
     * @param params
     * @return
     */
	@Override
	public Page<WpStrateSingleSumEntity> listWpStrateV1(Map<String, Object> params) {
		Query query = new Query(params);
		Page<WpStrateSingleSumEntity> page = new Page<>(query);
		wpStrateSingleSumMapper.listForPage(page, query);
		return page;
	}



    /**
     * 根据id查询
     * @param id
     * @return
     */
	@Override
	public R getWpStrateById(Long id) {
		WpStrateEntity wpStrate = wpStrateManager.getWpStrateById(id);
		return CommonUtils.msg(wpStrate);
	}

	/**
	 * 获取策略包明细
	 * @param strateInfoVo
	 * @return
	 */
	@Override
	public R getWpStrateInfo(StrateInfoVo strateInfoVo) {
		WpIceInfo wpIceInfo = wpStrateManager.selectGoldByUserId(strateInfoVo.getUserId());
		strateInfoVo = wpStrateManager.getWpStrateInfo(strateInfoVo);
		if (wpIceInfo!=null) {
			strateInfoVo.setGold(wpIceInfo.getIceHaveMoney());
			return CommonUtils.msg(strateInfoVo);
		}else {
			return R.error("用户信息不存在");
		}

	}

	@Override
	public R getGold(StrateInfoVo strateInfoVo) {
		WpIceInfo wpIceInfo = wpStrateManager.selectGoldByUserId(strateInfoVo.getUserId());
//		Integer gold = 500;
		if (wpIceInfo!=null) {
			UserGoldCount userGoldCount = new UserGoldCount();
			userGoldCount.setGold(wpIceInfo.getIceHaveMoney()-wpIceInfo.getIceGetMoney());
			userGoldCount.setNickName(wpIceInfo.getNickName());
			return CommonUtils.msg(userGoldCount);
		}else {
			return R.error("未获取到用户钻石信息");
		}

	}

	@Override
	public R packaging(StrateInfoVo strateInfoVo) {
		List<PackInfo> packInfo = wpStrateManager.packaging(strateInfoVo.getUserId());
		return CommonUtils.msg(packInfo);
	}

    @Override
    public R saveOrer(OrderVo orderVo) {
	    Boolean flag = true;
	    String message ="";
        try {
			wpStrateManager.saveOrer(orderVo);
        } catch (Exception e) {
           flag=false;
           message = e.getMessage();
        }
        if (flag) {
            return R.ok("购买成功");
        }else {
            return R.error(message);
        }

    }

    @Override
    public R saveOrerV1(OrderVo orderVo) {
	    Boolean flag = true;
	    String message ="";
        try {
			wpStrateManager.saveOrerV1(orderVo);
        } catch (Exception e) {
           flag=false;
           message = e.getMessage();
        }
        if (flag) {
            return R.ok("购买成功");
        }else {
            return R.error(message);
        }

    }

	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	@Override
	public Page<WpRecordVo> listRecord(Map<String, Object> params) {
		Query query = new Query(params);
		Page<WpRecordVo> page = new Page<>(query);
		wpStrateManager.listRecord(page, query);
		return page;
	}

	@Override
	public R packagType(StrateInfoVo strateInfoVo) {
		WpStragePriceList wpStragePriceList = new WpStragePriceList();
		List<WpStragePrice> list = wpStrateManager.packagType();
		WpIceInfo wpIceInfo = wpStrateManager.selectGoldByUserId(strateInfoVo.getUserId());
		for (WpStragePrice wpStragePrice : list) {
			//查看用户是否已购买
			WpStrategyDetailEntity wpStrategyDetailEntity = wpStrategyDetailManager.selectByUserId(wpStragePrice.getType(),strateInfoVo.getUserId());
			if (wpStrategyDetailEntity==null) {
				wpStragePrice.setStatus(0);//是否已购买: 1. 是   0. 否
			}else {
				if (wpStrategyDetailEntity.getDayCount()==-1) {
					wpStragePrice.setStatus(1);//是否已购买: 1. 是   0. 否
					wpStragePrice.setStartDate(wpStrategyDetailEntity.getStartDate());
					wpStragePrice.setEndDate(wpStrategyDetailEntity.getEndDate());
				}else {
					if (wpStrategyDetailEntity.getStartDate().before(new Date()) && wpStrategyDetailEntity.getEndDate().after(new Date())) {
						wpStragePrice.setStatus(1);//是否已购买: 1. 是   0. 否
						wpStragePrice.setStartDate(wpStrategyDetailEntity.getStartDate());
						wpStragePrice.setEndDate(wpStrategyDetailEntity.getEndDate());
					}else {
						wpStragePrice.setStatus(0);//是否已购买: 1. 是   0. 否
					}
				}
			}
		}
		wpStragePriceList.setList(list);
		if (wpIceInfo!=null){
			wpStragePriceList.setTotalGold(wpIceInfo.getIceHaveMoney().intValue());
			return CommonUtils.msg(wpStragePriceList);
		}else {
			return R.error("用户信息不存在");
		}
	}

		@Override
		public R packagTypeV1(StrateInfoVo strateInfoVo) {
		//1.先查询wp_strate_pack_sum (打包策略包汇总)，如果没有先初始化
		List<WpStratePackSumEntity>	 stratePackSumList =  wpStratePackSumManager.getWpStratePackSumByUserId(strateInfoVo);//userId和type
		 WpIceInfo wpIceInfo = wpStrateManager.selectGoldByUserId(strateInfoVo.getUserId());
			WpStratePackSumList wpStratePackSumList = new WpStratePackSumList();
			wpStratePackSumList.setList(stratePackSumList);
			if (wpIceInfo != null) {
				wpStratePackSumList.setTotalGold(wpIceInfo.getIceHaveMoney().intValue());
				return CommonUtils.msg(wpStratePackSumList);
			} else {
				return R.error("用户信息不存在");
			}
		}




	/**
	 * 批量初始化用户打包购买策略包汇总
	 * @param userId
	 * @return
	 */
		private List<WpStratePackSumEntity>  getStratePackSumList(Integer userId){
			List<WpStratePackSumEntity> list = new ArrayList<>();
			WpStratePackSumEntity wpStratePackSumEntity = null;
			for (int i = 1; i <= 4; i++) {
				wpStratePackSumEntity = new WpStratePackSumEntity();
				wpStratePackSumEntity.setPoolType(i);
				wpStratePackSumEntity.setUserId(userId);
				wpStratePackSumEntity.setType(2);//6人桌
				list.add(wpStratePackSumEntity);
			}
			for (int i = 1; i <= 4; i++) {
				wpStratePackSumEntity = new WpStratePackSumEntity();
				wpStratePackSumEntity.setPoolType(i);
				wpStratePackSumEntity.setUserId(userId);
				wpStratePackSumEntity.setType(3);//8人桌
				list.add(wpStratePackSumEntity);
			}
			return list;
		}

	private List<WpStratePackSumEntity> getStratePackSumListV1(Integer userId,WpStrategyDetailEntity wpStrategyDetailEntity, int a) {
		List<WpStratePackSumEntity> list = new ArrayList<>();
		WpStratePackSumEntity wpStratePackSumEntity = null;
		Integer otherType = 0;
		if(wpStrategyDetailEntity.getType()==2){
			otherType=3;
		}
		if(wpStrategyDetailEntity.getType()==3){
			otherType=2;
		}
		//全部策略包
		if (wpStrategyDetailEntity.getTypeNum().contains("-4")) {
	/*		if (a==-1) {//永久
			}else {
			}*/
			for (int i = 1; i <= 4; i++) {
				wpStratePackSumEntity = new WpStratePackSumEntity();
				wpStratePackSumEntity.setPoolType(i);
				wpStratePackSumEntity.setUserId(userId);
				wpStratePackSumEntity.setType(wpStrategyDetailEntity.getType());
				wpStratePackSumEntity.setStartTime(wpStrategyDetailEntity.getStartDate());
				wpStratePackSumEntity.setEndTime(wpStrategyDetailEntity.getEndDate());
				list.add(wpStratePackSumEntity);
			}
			for (int i = 1; i <= 4; i++) {
				wpStratePackSumEntity = new WpStratePackSumEntity();
				wpStratePackSumEntity.setPoolType(i);
				wpStratePackSumEntity.setUserId(userId);
				wpStratePackSumEntity.setType(otherType);
				list.add(wpStratePackSumEntity);
			}
		//非全部策略包
		}else {
/*			if (a==-1) {//永久
			}else {
			}*/
			for (int i = 1; i <= 4; i++) {
				if(wpStrategyDetailEntity.getPoolType()==i){
					wpStratePackSumEntity = new WpStratePackSumEntity();
					wpStratePackSumEntity.setPoolType(i);
					wpStratePackSumEntity.setUserId(userId);
					wpStratePackSumEntity.setType(wpStrategyDetailEntity.getType());
					wpStratePackSumEntity.setStartTime(wpStrategyDetailEntity.getStartDate());
					wpStratePackSumEntity.setEndTime(wpStrategyDetailEntity.getEndDate());
					list.add(wpStratePackSumEntity);
				}else {
					wpStratePackSumEntity = new WpStratePackSumEntity();
					wpStratePackSumEntity.setPoolType(i);
					wpStratePackSumEntity.setUserId(userId);
					wpStratePackSumEntity.setType(wpStrategyDetailEntity.getType());
					list.add(wpStratePackSumEntity);
				}

			}
			for (int i = 1; i <= 4; i++) {
				wpStratePackSumEntity = new WpStratePackSumEntity();
				wpStratePackSumEntity.setPoolType(i);
				wpStratePackSumEntity.setUserId(userId);
				wpStratePackSumEntity.setType(otherType);
				list.add(wpStratePackSumEntity);
			}
		}
		return list;
	}
}
