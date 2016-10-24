package com.webhybird.framework.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 敏感词过滤
 * @author wang zhong fu
 * @version 1.0
 */
public class SensitivewordUtil {
	/**
	 * 
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 单例
	 */
	private volatile static SensitivewordUtil instance = null;
	/**
	 * 最小匹配规则
	 */
	public static final int minMatchTYpe = 1;      //最小匹配规则
	/**
	 * 最大匹配规则
	 */
	public static final int maxMatchType = 2;      //最大匹配规则
	
	/**
	 * 构造函数
	 */
	private SensitivewordUtil(){
	}
	/**
	 * 
	 * @return
	 */
	public static SensitivewordUtil getInstance() {
	    if (instance == null) {
	      synchronized (SensitivewordUtil.class) {
	        if(instance == null) {
	          instance = new SensitivewordUtil();
	        }
	      }
	    }
	  return instance;
	}
	/**
	 * 敏感词库判空
	 */
	@SuppressWarnings("rawtypes")
	private boolean verifySensitiveLibrary(Map sensitiveWordMap){
		if(sensitiveWordMap == null || sensitiveWordMap.size() == 0){
			log.info("敏感词库为空");
			return true;
		}
		return false;
	}
	/**
	 * 判断文字是否包含敏感字符
	 * @author wang zhong fu 
	 * @param txt  文字
	 * @param matchType  匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @param sensitiveWordMap 敏感词库
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public boolean isContaintSensitiveWord(String txt,int matchType,Map sensitiveWordMap){
		if(verifySensitiveLibrary(sensitiveWordMap)){
			return false;
		}
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType,sensitiveWordMap); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取文字中的敏感词
	 * @author wang zhong fu 
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @param sensitiveWordMap 敏感词库
	 * @return
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public Set<String> getSensitiveWord(String txt , int matchType,Map sensitiveWordMap){
		if(verifySensitiveLibrary(sensitiveWordMap)){
			return null;
		}
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType,sensitiveWordMap);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		return sensitiveWordList;
	}
	
	/**
	 * 替换敏感字字符
	 * @author wang zhong fu 
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar,Map sensitiveWordMap){
		if(verifySensitiveLibrary(sensitiveWordMap)){
			return null;
		}
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType,sensitiveWordMap);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		return resultTxt;
	}
	
	/**
	 * 获取替换字符串
	 * @author wang zhong fu 
	 * @date 2014年4月20日 下午5:21:19
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		return resultReplace;
	}
	
	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * @author wang zhong fu
	 * @date 2014年4月20日 下午4:31:03
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType,Map sensitiveWordMap){
		if(verifySensitiveLibrary(sensitiveWordMap)){
			return 0;
		}
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1 
				if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true   
					if(SensitivewordUtil.minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			}
			else{     //不存在，直接返回
				break;
			}
		}
		if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词 
			matchFlag = 0;
		}
		return matchFlag;
	}
	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 * @author wang zhong fu 
	 * @param keyWordSet  敏感词库
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map addSensitiveWordToHashMap(final Set<String> keyWordSet) {
		if(keyWordSet == null || keyWordSet.size() == 0){
			return new HashMap(0);
		}
		HashMap sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //最后一个
				}
			}
		}
		return sensitiveWordMap;
	}
}
