package simple.packages.spring.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Customer implements Serializable {
	
	/*
	personally
	1>all these annotation seems unnecessary
	2>not necessary to use @JsonUnwrapped cus no reasson to flatten
	3>not necessary to use @JsonProperty with different name unless third party client 
	demand in different name?? but chance is you gonna use these pojo in restful aka 
	your api you are the boss. it is third party suppose to bend over no???
	*/

	/*
	we’ll use the `@JsonProperty` annotation to specify the names of the 
	fields in the JSON output
	*/
	@JsonProperty("full_name")
	private String name;
	
	/*
	We’ve specified the `Include.NON_DEFAULT` option, which means that the 
	“age” field will only be included in the JSON output if its value is not 
	the default value for its data type (which is 0 for `int`)
	*/
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Long age;
	
	/*
	1>without @JsonUnwrapped
	"full_nick_name" : {
      "first_nick_name" : "Joey",
      "last_nick_name" : "Sixpack"
    }
	2>with @JsonUnwrapped <--BAD IDEA, leave it for experiment purpose
	"first_nick_name" : "Joey",
    "last_nick_name" : "Sixpack"
	*/
	@JsonProperty("full_nick_name")
	@JsonUnwrapped
	private NickName nickName;
	
	@JsonProperty
	private String Hobby;
    
    /*
    The transient keyword in Java is used to indicate that a field should not be 
    part of the serialization
    /////////////////////////////
    We could also use the @JsonIgnore annotation to exclude certain fields from 
    the JSON output
    */
    @JsonIgnore
    private transient String ignore;
    
	public Customer() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public NickName getNickName() {
		return nickName;
	}

	public void setNickName(NickName nickName) {
		this.nickName = nickName;
	}

	@JsonGetter("weird_hobby")
	public String getHobby() {
		return Hobby;
	}

	@JsonSetter("weird_hobby")
	public void setHobby(String hobby) {
		Hobby = hobby;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", age=" + age + ", nickName=" + nickName + ", Hobby=" + Hobby + "]";
	}

}

/*
1>The FasterXML Jackson library is a popular Java library used for working with 
JSON data. It provides a number of annotations that can be used to customize the 
serialization and deserialization of JSON data 

2>@JsonIgnore: This annotation can be used to ignore a field during serialization 
and deserialization. Any field marked with this annotation will not be included 
in the JSON output or read from the JSON input.

3>@JsonProperty: This annotation can be used to specify the name of a field in the 
JSON output or input that differs from the name of the corresponding Java field. 
This can be useful when working with JSON data from external sources that use 
different naming conventions.

4>@JsonFormat: This annotation can be used to specify the format of a date or other 
value during serialization and deserialization. For example, you can use this 
annotation to specify that a date should be formatted as ISO-8601.

5>@JsonInclude: This annotation can be used to specify which fields should be included 
in the JSON output or input. You can use this annotation to include only non-null 
fields or to include fields with a specific value.
//////////////////
5>@JsonUnwrapped: This annotation can be used to unwrap the properties of a nested 
object during serialization and deserialization. This can be useful when you want to 
flatten a complex object hierarchy into a single JSON object.

6>@JsonSetter: This annotation can be used to customize the setter method used 
during deserialization. You can use this annotation to specify a different method 
name or to customize the way the value is converted from the JSON input to the 
Java object.

7>@JsonGetter: This annotation can be used to customize the getter method used 
during serialization. You can use this annotation to specify a different method 
name or to customize the value returned from the Java object to the JSON output.
*/
