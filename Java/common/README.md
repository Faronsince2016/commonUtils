# 基本说明
## 导入说明
1. 确认本地成功安装有MAVEN仓库
2. 打开cmd控制台（win+R，输入cmd，回车）
3. 在控制台输入下面的命令

        -Dfile:打包好的jar包的存放路径
        
        -DgroupId:组织名称
        
        -DartifactId:包名称
        
        -Dversion:对应包版本
        
        -Dpackaging:对应包类型

* 安装源码包；
    
        mvn install:install-file -Dfile=F:\LXW\java\common\target\common-0.0.1-SNAPSHOT-sources.jar   -DgroupId=cn
        .migu.log.util  -DartifactId=common-0.0.1-SNAPSHOT-sources  -Dversion=0.0.1  -Dpackaging=jar
    
* 安装基础包:
    
        mvn install:install-file -Dfile=F:\LXW\java\common\target\common-0.0.1-SNAPSHOT.jar   -DgroupId=cn.migu.log
        .util  -DartifactId=common-0.0.1-SNAPSHOT  -Dversion=0.0.1  -Dpackaging=jar
        
4. 回车运行，出现提示则为成功
    
        [INFO] Scanning for projects...
        [INFO]
        [INFO] ------------------< org.apache.maven:standalone-pom >-------------------
        [INFO] Building Maven Stub Project (No POM) 1
        [INFO] --------------------------------[ pom ]---------------------------------
        [INFO]
        [INFO] --- maven-install-plugin:2.4:install-file (default-cli) @ standalone-pom ---
        [INFO] Installing F:\LXW\java\common\target\common-0.0.1-SNAPSHOT.jar to C:\Users\Carol\.m2\repository\cn\migu\log\cn.migu.log.util\0.0.1\cn.migu.log.util-0.0.1.jar
        [INFO] Installing C:\Users\Carol\AppData\Local\Temp\mvninstall623250773444033360.pom to C:\Users\Carol\.m2\repository\cn\migu\log\cn.migu.log.util\0.0.1\cn.migu.log.util-0.0.1.pom
        [INFO] ------------------------------------------------------------------------
        [INFO] BUILD SUCCESS
        [INFO] ------------------------------------------------------------------------
        [INFO] Total time: 1.340 s
        [INFO] Finished at: 2018-10-31T11:10:50+08:00
        [INFO] ------------------------------------------------------------------------

5. 在你需要使用该jar包的地方使用下面的引入即可

        //源码包
        <dependency>
            <groupId>cn.migu.log.util</groupId>
            <artifactId>common-0.0.1-SNAPSHOT-sources</artifactId>
            <version>0.0.1</version>
        </dependency>
        //基础包
        <dependency>
            <groupId>cn.migu.log.util</groupId>
            <artifactId>common-0.0.1-SNAPSHOT</artifactId>
            <version>0.0.1</version>
        </dependency>
        
## 使用RedisUtil

1. 初始化RedisUtil，可以新建一个 @Component 继承 SmartLifecycle，然后引入下面的两行代码

        @Resource(name = "redisUtil")
        private RedisUtil redisUtil;

2. 新建 RedisConfig.java，创建 redis 对象

        @Configuration
        public class RedisConfig {
        
            //统一前缀名
            private String redisName = "";
        
            @Bean
            public RedisTemplate<String, Object> functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
                RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
                initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
                return redisTemplate;
            }
        
            private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
                //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                redisTemplate.setHashKeySerializer(new StringRedisSerializer());
                redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
                redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
                // 开启事务
                redisTemplate.setEnableTransactionSupport(true);
                redisTemplate.setConnectionFactory(factory);
            }
        
            @Bean(name = "redisUtil")
            public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
                RedisUtil redisUtil = new RedisUtil();
                redisUtil.setRedisTemplate(redisTemplate);
                redisUtil.setRedisName(redisName);
                return redisUtil;
            }
        
        }
        
3. 新建 RedisObjectSerializer.java 序列化对象

        public class RedisObjectSerializer implements RedisSerializer<Object> {
            private Converter<Object, byte[]> serializer = new SerializingConverter();
            private Converter<byte[], Object> deserializer = new DeserializingConverter();
            static final byte[] EMPTY_ARRAY = new byte[0];
        
            public Object deserialize(byte[] bytes) {
                if (isEmpty(bytes)) {
                    return null;
                }
                try {
                    return deserializer.convert(bytes);
                } catch (Exception ex) {
                    throw new SerializationException("Cannot deserialize", ex);
                }
            }
        
            public byte[] serialize(Object object) {
                if (object == null) {
                    return EMPTY_ARRAY;
                }
                try {
                    return serializer.convert(object);
                } catch (Exception ex) {
                    return EMPTY_ARRAY;
                }
            }
        
            private boolean isEmpty(byte[] data) {
                return (data == null || data.length == 0);
            }
        }

4. 在配置文件中配置redis的相关参数

        spring.redis.host=
        spring.redis.port=
        spring.redis.password=

5. 在需要使用的java项目中注入后即可使用

        @Autowired
        private RedisUtil redisUtil;
    
        @Test
        public void test(){
            System.out.println(redisUtil.set("test", "pomtest"));
        }

## DateTimeUtil、ExceptionInfoUtil、FileOperationUtil、JacksonUtil 可以直接使用

## HttpCommonUtil 的使用

1. 初始化 RestTemplate，可以新建一个 @Component 继承 SmartLifecycle，然后引入下面的两行代码

        @Bean(name = "restTemplate")
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
        
2. 在需要使用的java项目中注入后即可使用
   
        @Autowired
        private RestTemplate restTemplate;
               
        @Test
        public void test(){
            System.out.println(restTemplate.postVieObjectReturn(restTemplate, "test", "localhost:18080/start"));
        }

