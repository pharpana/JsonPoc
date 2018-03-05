package com.venara.jsonpoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.venara.vo.Topic;

public class TopicManager {
	
	public void getToken() {
		
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader("Tags.txt"));
			String line = "";
			Topic globalTopic = new Topic();
			while((line = bufferedReader.readLine()) !=null) {
				System.out.println("Line: "+ line);
				
				StringTokenizer st = new StringTokenizer(line, " ");
				int tokenNum = 0;
				Topic parent = new Topic();
				Topic currentTopic = new Topic();
				while(st.hasMoreTokens()) {
					String strToken = st.nextToken();
					tokenNum++;
					
					currentTopic = new Topic();
					currentTopic.setName(strToken);
					currentTopic.setSize(1);
					currentTopic.setParentName(parent.getName());
					
					setInGlobalTopic(globalTopic, currentTopic, tokenNum);
					
					parent = currentTopic;
				}
			}
			
			System.out.println(globalTopic);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void setInGlobalTopic(Topic globalTopic, Topic topic, int level) {
		// if no topics present
		if(globalTopic.getName() == null) {
			globalTopic.setName(topic.getName());
			globalTopic.setParentName(topic.getParentName());
			globalTopic.setSize(topic.getSize());
			globalTopic.setChildren(topic.getChildren());
			return;
		}
		
		// if globaltopic == topic
		// increment globaltopic size
		if(globalTopic.getName().equalsIgnoreCase(topic.getName())) {
			int currentSize = globalTopic.getSize();
			globalTopic.setSize(++currentSize);
			return;
		}
		
		if(level == 2)
			addInLevel2(globalTopic, topic);
		else if(level == 3)
			addInLevel3(globalTopic, topic);
		else if(level == 4)
			addInLevel4(globalTopic, topic);
		else if(level == 5)
			addInLevel5(globalTopic, topic);
	}
	
	private void addInLevel2(Topic globalTopic, Topic topic) {
		if(globalTopic.getChildren() == null) {
			List<Topic> topics = new ArrayList<Topic>();
			topics.add(topic);
			globalTopic.setChildren(topics);
			return;
		}
		
		if(globalTopic.getChildren().size() == 0) {
			List<Topic> topics = globalTopic.getChildren();
			topics.add(topic);
			globalTopic.setChildren(topics);
			return;
		}
		
		boolean alreadyPresent = false;
		for(Topic child :globalTopic.getChildren()) {
			// if already present
			if(child.getName().equalsIgnoreCase(topic.getName())) {
				int count = child.getSize();
				child.setSize(++count);
				alreadyPresent = true;
			}
		}
		
		if(!alreadyPresent) {
			globalTopic.getChildren().add(topic);
		}
	}
	
	private boolean addInLevel3(Topic globalTopic, Topic topic) {
		boolean flag = false;
		if(globalTopic.getChildren() == null) {
			return flag;
		}
		
		for(Topic parentTopics : globalTopic.getChildren()) {
			if(parentTopics.getName().equalsIgnoreCase(topic.getParentName())) {
				addInLevel2(parentTopics, topic);
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	private boolean addInLevel4(Topic globalTopic, Topic topic) {
		boolean flag = false;
		
		if(globalTopic.getChildren() == null) {
			return flag;
		}
		
		for(Topic gParentTopic : globalTopic.getChildren()) {
			flag = addInLevel3(gParentTopic, topic);
			if(flag) {
				break;
			}
		}
		
		return flag;
	}
	
	private boolean addInLevel5(Topic globalTopic, Topic topic) {
		boolean flag = false;
		
		if(globalTopic.getChildren() == null) {
			return flag;
		}
		
		for(Topic gParentTopic : globalTopic.getChildren()) {
			flag = addInLevel4(gParentTopic, topic);
			if(flag) {
				break;
			}
		}
		
		return flag;
	}

}
