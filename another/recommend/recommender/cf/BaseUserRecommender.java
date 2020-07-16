package com.recommender.cf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * 基于用户的推荐
 * 
 * @author Lenovo
 *
 */
public class BaseUserRecommender {

	private static final String HDFS = "hdfs://ecs-hadoop-master:9000";

	public static void main(String[] args) throws Exception {
		List<Long> itemIds = BaseUserRecommender.getUserRecommend( 1,2);
		System.out.println(itemIds);
	}

	public static List<Long> getUserRecommend(int userId,int howMany) throws Exception{

		//String data = GetData.getHttpRequestData("http://kunpeng-web.oranme.com/interest/get");
		//System.out.println(data);

		String file = "D:\\GoodGoodStudyDayDayUp\\2020summer\\project\\bookshop_ml\\src\\main\\java\\data\\item.csv";
		// 将数据加载到内存中
		DataModel dataModel = new FileDataModel(new File(file));
		// 计算相似度
		UserSimilarity similarity = new EuclideanDistanceSimilarity(dataModel);
		// 计算最近邻域，邻居有两种算法，基于固定数量的邻居和 基于相似度的邻居，这里使用基于固定数量的邻居
		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(5, similarity, dataModel);
		// 构建推荐器，协同过滤推荐有多种，如：基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
		Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        // 推荐商品
		List<RecommendedItem> list = recommender.recommend(userId, howMany);
		List<Long> itemIds = new ArrayList<Long>();
		for (RecommendedItem ritem : list) {
			System.out.println(ritem);
			itemIds.add(ritem.getItemID());
		}
		System.out.println("推荐的商品有:"+itemIds);

		return itemIds;
	}
}
