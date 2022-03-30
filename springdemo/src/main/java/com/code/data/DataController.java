package com.code.data;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/10/5.
 * 数据库操作 demo
 */
@RestController
@Slf4j
public class DataController {

    @Autowired
    private UserTestDao userTestDao;

    @Autowired
    private VideoTranscodeDao videoTranscodeDao;

//     final  PeopleEntity  x ;

    static {
        System.out.println("static静态初始化块");
    }


    {
        System.out.println("初始化块");
    }

    public DataController() {
    }


    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping(value = "/peoples")
    public List<UserTestEntity> peoples() {
        return userTestDao.findAll();
    }

    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/peoples")
    public UserTestEntity addPeople(@RequestParam String name, @RequestParam Integer age) {
        UserTestEntity peopleEntity = new UserTestEntity();
        return userTestDao.save(peopleEntity);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/peoples/{id}")
    public UserTestEntity findOne(@PathVariable Integer id) {
        return userTestDao.findById(id).get();
    }

    /**
     * 根据username查询
     *
     * @param username
     * @return
     */
    @GetMapping("/peoples/username/{usernaem}")
    public List<UserTestEntity> findByAge(@PathVariable String username) {
        return userTestDao.findByUsername(username);
    }

    /**
     * 更新
     *
     * @param id
     * @param name
     * @param age
     * @return
     */
    @PutMapping("/peoples/{id}")
    public UserTestEntity update(@PathVariable Integer id, @RequestParam String name, @RequestParam Integer age) {
        UserTestEntity peopleEntity = new UserTestEntity();
        peopleEntity.setId(id);
        Integer x = 0;
        return userTestDao.save(peopleEntity);
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/peoples/{id}")
    public void delete(@PathVariable Integer id) {
        userTestDao.deleteById(id);
    }

    @PostMapping("/query")
    public void query(@RequestParam(required = false) Integer id, HttpServletRequest request) throws IOException {
        request.getInputStream();
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap);
        request.getInputStream();
        System.out.println(id);
    }

    @GetMapping("/data/add")
    public String addVideo() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Date now = new Date();
        ArrayList<VideoTranscode> objects = Lists.newArrayList();
        for (int i = 0; i < 300000; i++) {
            int randomInt = -RandomUtil.randomInt(100);
            DateTime dateTime = DateUtil.offsetDay(now, randomInt);
            String fileID = RandomUtil.randomNumbers(20);
            VideoTranscode videoTranscode = new VideoTranscode().setDefinition(10).setFileId(fileID).setCreatedTime(dateTime).setUrl("").setSize(0L);
            objects.add(videoTranscode);
            if(objects.size() ==50){
                System.out.println("do add");
                ArrayList<VideoTranscode> finalObjects = objects;
                executorService.execute(()-> {
                    videoTranscodeDao.saveAll(finalObjects);
                    finalObjects.clear();
                });
                objects= Lists.newArrayList();

            }
        }
        return "finish";
    }


    @GetMapping("/data/array")
    public String dataArray(Integer[] arr) {
        log.info(JSONUtil.toJsonStr(arr));
        return "ok";
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        objPoolTest();
//        Class.forName("com.code.data.DataController");
//        new DataController();
//        new TreeMap();
//        new ConcurrentHashMap<String,String>();

        int score[] = {67, 69, 75, 87, 89, 90, 99, 100};
        for (int i = 0; i < score.length; i++) {
            for (int j = 0; j < score.length - i - 1; j++) {
                if (score[j] < score[j + 1]) {
                    int temp = score[j];
                    score[j] = score[j + 1];
                    score[j + 1] = temp;
                }
            }
        }

        System.out.print("最终排序结果：");
        for (int a = 0; a < score.length; a++) {
            System.out.print(score[a] + "\t");
        }


    }

    public static void objPoolTest() {
        int i = 40;
        int i0 = 40;
        Integer i1 = 40;
        Integer i2 = 40;
        Integer i3 = 0;
        Integer i4 = new Integer(40);
        Integer i5 = new Integer(40);
        Integer i6 = new Integer(0);
        Double d1 = 1.0;
        Double d2 = 1.0;

//        System.out.println("i=i0\t" + (i == i0));
//        System.out.println("i1=i2\t" + (i1 == i2));
//        System.out.println("i1=i2+i3\t" + (i1 == i2 + i3));
//        System.out.println("i4=i5\t" + (i4 == i5));
//        System.out.println("i4=i5+i6\t" + (i4 == i5 + i6));
//        System.out.println("d1=d2\t" + (d1==d2));

        System.out.println();
    }
}

