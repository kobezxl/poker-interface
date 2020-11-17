package com.cn.poker.common.util;
import com.cn.poker.common.constant.MsgConstant;
import com.cn.poker.common.constant.SystemConstant;
import com.cn.poker.common.entity.R;
import org.apache.shiro.SecurityUtils;

import java.util.List;
import java.util.UUID;

/**
 * 通用工具类
 */
public class CommonUtils {

	public static final int ERROR = -1;

	public static final int SUCCESS = 1;

	/**
	 * 对象是否为空
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 判断整数是否大于零
	 *
	 * @param number
	 * @return
	 */
	public static boolean isIntThanZero(int number) {
		if (number > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 新增，修改提示
	 * @param count
	 * @return
	 */
	public static R msg(int count) {
		if(isIntThanZero(count)){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}
		return R.error(MsgConstant.MSG_OPERATION_FAILED);
	}

	/**
	 * 查询详情提示
	 * @param data
	 * @return
	 */
	public static R msg(Object data) {
		if(isNullOrEmpty(data)){
			return R.error(MsgConstant.MSG_INIT_FORM);
		}
		return R.ok().put(SystemConstant.DATA_ROWS, data);
	}

	/**
	 * 返回自定义结果
	 * @param count
	 * @param msg
	 * @return
	 */
	public static R msg(int count,String msg) {
		if(isIntThanZero(count)){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}
		return R.error(msg);
	}

	/**
	 * 返回图片结果
	 * @param url
	 * @return
	 */
	public static R msg(String url) {
		if(isNullOrEmpty(url)){
			return R.error(MsgConstant.MSG_INIT_FORM);
		}
		return R.ok().put("msg", url);
	}

	/**
	 * 返回数据
	 * @param data
	 * @return
	 */
	public static R msgNotCheckNull(Object data) {
		return R.ok().put(SystemConstant.DATA_ROWS, data);
	}

	/**
	 * 删除提示
	 * @param total
	 * @param count
	 * @return
	 */
	public static R msg(Object[] total, int count) {
		if(total.length == count){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}else{
			if(isIntThanZero(count)){
				return R.error(MsgConstant.removeFailed(total.length, count));
			}else{
				return R.error(MsgConstant.MSG_OPERATION_FAILED);
			}
		}
	}


	/**
	 * 获得uuid
	 * @return
	 */
	public static String getuuid(){
		UUID uuid=UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr=str.replace("-", "");
		return uuidStr;
	}



	/**
	 * 富文本处理1
	 */
	public static String disposeContent(String Content){
		if(!(Content == null || Content.equals("")  || Content == "")) {
			Content = Content.replace("&lt;", "<");
			Content = Content.replace("&gt;", ">");
			Content = Content.replace("&amp;nbsp;","&nbsp;");
		}
		return Content;
	}

	/**
	 * 富文本处理2
	 */
	public static String replace(String content) {
		if(!(content == null || content.equals("")  || content == "")) {
			content = content.replace("0.26666rem", "x-small");
			content = content.replace("0.32rem", "small");
			content = content.replace("0.37333rem", "medium");
			content = content.replace("0.5333rem", "xx-large");
			content = content.replace("0.48rem", "x-large");
			content = content.replace("0.42666rem", "large");
		}
		return content;
	}

	public static R msgs(int count) {
		if(isIntThanZero(count)){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}
		return R.error("项目名称已存在!");
	}
}
