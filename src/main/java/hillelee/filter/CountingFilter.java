package hillelee.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class CountingFilter implements Filter{

    private final AtomicInteger counter =new AtomicInteger();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        int i=counter.incrementAndGet();

        log.warn("incoming request number: " + i);

        chain.doFilter(request, response);

        log.warn("outcoming request number: " + i);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
