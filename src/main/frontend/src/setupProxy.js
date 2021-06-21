const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/ws/**',
        createProxyMiddleware({
            target: 'http://localhost:8082/',
            changeOrigin: true,
            ws: true,
            logLevel: 'debug',
        })
    );
};
