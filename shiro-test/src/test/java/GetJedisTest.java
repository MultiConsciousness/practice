import com.shirotest.dao.RedisSessionDao;
import com.shirotest.util.Jedisutil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-redis.xml","classpath:spring/applicationContext-shiro.xml"})
public class GetJedisTest {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Jedisutil jedisutil;
    @Autowired
    private RedisSessionDao redisSessionDao;


    @Test
    public void setValue(){
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set("test","aoyidei");
        }finally {
            jedis.close();
        }
    }

    @Test
    public void setValueByJedisutil(){
        jedisutil.set("a".getBytes(),"b".getBytes());
    }

}
