package simple.packages.spring.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NickName implements Serializable {

	@JsonProperty("first_nick_name")
	public String first;
	
	@JsonProperty("last_nick_name")
	public String last;
	
	public NickName() {
			super();
	}
	
	/*
	example of using the @JsonCreator annotation to customize the constructor 
	used during deserialization
	//////////////////////
	1>Recall that ObjectMapper expects getters for serialization—and it wants them 
	to conform to the JavaBeans get/setFoo() convention. 
	2>ObjectMapper also expects an accessible default constructor, that is, one 
	that takes no parameters
	//////////////////////
	3>technically if you have default construct and get/set and field annotation
	you dont need to annotate param constructor...
	this approach is probably optimal MAYBE?
	//////////////////////
	4>but if say you have NO default construct and NO get/set, then only field 
	annotation is useless...  
	5>then you need fallback option annotation on param construct.  this class 
	dont need it, just show example...
	////////////////////////
	odd this didnt work EPIC FAILED???  forced me to do field annotation.,.
	AVOID construct annotation overrated crap... stick with field annotation....
	*/
	@JsonCreator
	public NickName(
		@JsonProperty("first_nick_name")  String first, 
		@JsonProperty("last_nick_name")  String last) {
		
		super();
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Override
	public String toString() {
		return "NickName [first=" + first + ", last=" + last + "]";
	}
	
}
