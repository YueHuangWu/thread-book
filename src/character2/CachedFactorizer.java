package character2;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Administrator on 2020/2/10 0010.
 */
public class CachedFactorizer implements Servlet {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;
    private long hits;
    private long cacheHits;

    public synchronized long getHits(){
        return hits;
    }

    public synchronized double getCacheHitRatio(){
        return (double) cacheHits / (double) hits;
    }

    /**
     * 原子性，只会有一把锁执行，一个方法。
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
       BigInteger i = extractFromRequest(req);
       BigInteger[] factors = null;
       synchronized (this){
           ++hits;
           if(i.equals(lastNumber)){
               ++cacheHits;
               factors = lastFactors.clone();
           }
       }
       if(factors == null){
           //将耗时长的代码放到锁外面
           factors = factor(i);
           synchronized (this){
               lastNumber = i;
               lastFactors = factors.clone();
           }
       }
       encodeIntoResponse(res,factors);
    }

    private void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }

    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[0];
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
