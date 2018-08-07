package com.zcj.ls.ls_web;

import com.zcj.ls.ls_web.config.WebConfig;
import com.zcj.ls.ls_web.utils.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LsWebApplicationTests {

	@Autowired
	private WebConfig webConfig;

	/**
	 * 获取yml文件常量测试
	 */
	@Test
	public void testConfig(){
		Assert.assertEquals("C:/ls_web/upload/", webConfig.getUploadPath());
		System.out.println(webConfig.getUploadPath());
	}

	/**
	 * 登录养老平台
	 */
	@Test
	public void testLogin() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("cusId", "");
			jsonObject.put("cusName", "李四");
			jsonObject.put("cusMobile", "15893749273");
			jsonObject.put("address", "江苏省南京市溧水区");
		} catch (JSONException e) {
			System.out.println("Json对象封装失败！！！");
			e.printStackTrace();
		}
        JSONObject result = HttpUtil.getData("http://61.153.190.75:8081/WorkManage/interface/APP/userLogin.action", jsonObject);

        System.out.println(jsonObject);
	}
	/**
	 * 获取服务大类列表
	 */
	@Test
	public void testGetServiceCategory() {
		JSONObject jsonObject = new JSONObject();
        JSONObject result = HttpUtil.getData("http://61.153.190.75:8081/WorkManage/interface/APP/getServiceCategory.action", jsonObject);
		JSONArray categoryList = (JSONArray) result.get("categoryList");
        System.out.println(categoryList);
		String categoryId = (String) ((JSONObject) categoryList.get(3)).get("categoryId");

		jsonObject.put("categoryId", categoryId);
		result = HttpUtil.getData("http://61.153.190.75:8081/WorkManage/interface/APP/getServiceItem.action", jsonObject);
		JSONArray serviceItemList = (JSONArray) result.get("itemList");
		String serviceItemId = (String) ((JSONObject) serviceItemList.get(3)).get("itemId");
		System.out.println(serviceItemList);
	}
	/**
	 * 获取服务项目列表
	 */
	@Test
	public void testGetServiceItem() {
	}



}
