package com.yue.vertx;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VertxTest {

    public static void main(String[] args) {
        /**
         * 使用Vert.x进行开发离不开Vertx对象。
         *
         * 它是 Vert.x 的控制中心，也是您做几乎一切事情的基础，包括创建客户端和服务器、 获取事件总线的引用、设置定时器等等。
         */
        // Vertx vertx = Vertx.vertx();
        // 创建 Vertx 对象时指定配置项
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));

        /**
         *创建集群模式的 Vert.x 对象
         * 如果您想创建一个 集群模式的 Vert.x 对象（参考 event bus 章节了解更多事件总线集群细节）， 那么通常情况下您将需要使用另一种异步的方式来创建 Vertx 对象。
         *
         * 这是因为让不同的 Vert.x 实例组成一个集群需要一些时间（也许是几秒钟）。 在这段时间内，我们不想去阻塞调用线程，所以我们将结果异步返回给您。
         */
        // 注意要添加对应的集群管理器依赖，详情见集群管理器章节
//        VertxOptions options = new VertxOptions();
//        Vertx.clusteredVertx(options, res -> {
//            if (res.succeeded()) {
//                Vertx vertx = res.result(); // 获取到了集群模式下的 Vertx 对象
//                // 做一些其他的事情
//                System.out.println("vertx = " + vertx);
//            } else {
//                // 获取失败，可能是集群管理器出现了问题
//            }
//        });
//
//        vertx.setPeriodic(1000, id -> {
//            // 这个处理器将会每隔一秒被调用一次
//            System.out.println("timer fired!");
//        });

        /**
         * Future的异步结果
         * Vert.x 4使用future承载异步结果。
         *
         * 异步的方法会返回一个Future对象，其包含 成功 或 失败 的异步结果。
         *
         * 我们不能直接操作future的异步结果，而应该设置future的handler；当future执行完毕，结果可用时，会调用handler进行处理。
         */
//        FileSystem fs = vertx.fileSystem();
//        Future<FileProps> future = fs.props("D:\\code\\yue-project\\ume\\back-end\\ume\\vertx-demo\\src\\main\\resources\\data.txt");
//        future.onComplete((AsyncResult<FileProps> ar) -> {
//            if (ar.succeeded()) {
//                FileProps props = ar.result();
//                System.out.println("File size = " + props.size());
//            } else {
//                System.out.println("Failure: " + ar.cause().getMessage());
//            }
//        });

        /**
         * Future组合
         * compose方法作用于顺序组合 future：若当前future成功，执行 compose 方法指定的方法，该方法返回新的future；
         * 当返回的新future完成时，future组合成功；
         * 若当前future失败，则future组合失败。
         */
        FileSystem fs = vertx.fileSystem();

        Future<Void> future = fs
                .createFile("/foo")
                .compose(v -> {
                    // createFile文件创建完成后执行
                    return fs.writeFile("/foo", Buffer.buffer());
                })
                .compose(v -> {
                    // writeFile文件写入完成后执行
                    return fs.move("/foo", "/bar");
                });

    }

}