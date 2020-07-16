package com.recommender.cf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 * 基于物品的推荐
 * 
 * @author Lenovo
 *
 */
public class BaseItemRecommender {

	public static void main(String[] args) throws Exception {
		List<Long> itemIds = BaseItemRecommender.getItemRecommend( 1,103,2);
		System.out.println(itemIds);
	}

	public static List<Long> getItemRecommend(int userId,int itemId,int howMany) throws Exception{

		//String data = GetData.getHttpRequestData("http://kunpeng-web.oranme.com/intere st/get");
		//System.out.println(data);

		// 准备数据
		File file = new File("D:\\GoodGoodStudyDayDayUp\\2020summer\\project\\bookshop_ml\\src\\main\\java\\data\\item.csv");
		// 将数据加载到内存中
		DataModel dataModel = new FileDataModel(file);
		// 计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
		ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity(dataModel);
		// 构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

		List<RecommendedItem> list = recommender.recommendedBecause(userId,itemId, howMany);
		List<Long> itemIds = new ArrayList<Long>();
		for (RecommendedItem ritem : list) {
			System.out.println(ritem);
			itemIds.add(ritem.getItemID());
		}
		System.out.println("推荐的商品有:"+itemIds);

		return itemIds;
	}
}
