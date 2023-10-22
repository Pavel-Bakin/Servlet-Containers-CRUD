package ru.netology.servlet;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import ru.netology.confiq.SpringJavaConfig;
import ru.netology.controller.PostController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringJavaConfig.class);
        context.refresh();
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            if (GET_METHOD.equals(method) && "/api/posts".equals(path)) {
                controller.all(resp);
                return;
            }
            if (GET_METHOD.equals(method) && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.getById(id, resp);
                return;
            }
            if (POST_METHOD.equals(method) && "/api/posts".equals(path)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (DELETE_METHOD.equals(method) && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

