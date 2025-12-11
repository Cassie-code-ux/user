package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 统一用户
 * 后端接口
 * @author
 * @email
 */
@RestController
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        // 统一用户后无需区分角色查询条件
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = userService.queryPage(params);

        //字典表数据转换
        List<UserView> list =(List<UserView>)page.getList();
        for(UserView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        UserEntity user = userService.selectById(id);
        if(user !=null){
            //entity转view
            UserView view = new UserView();
            BeanUtils.copyProperties( user , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,user:{}",this.getClass().getName(),user.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));

        Wrapper<UserEntity> queryWrapper = new EntityWrapper<UserEntity>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("id_number", user.getIdNumber())
                .andNew()
                .eq("delete_status", 1)
                ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        UserEntity userEntity = userService.selectOne(queryWrapper);
        if(userEntity==null){
            user.setCreateTime(new Date());
            user.setPassword("123456");
            user.setDeleteStatus(1);
            userService.insert(user);
            return R.ok();
        }else {
            return R.error(511,"账户、联系方式或身份证号已被使用");
        }
    }

    /**
     * 后端修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,user:{}",this.getClass().getName(),user.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        //根据字段查询是否有相同数据
        Wrapper<UserEntity> queryWrapper = new EntityWrapper<UserEntity>()
                .notIn("id",user.getId())
                .andNew()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("id_number", user.getIdNumber())
                .andNew()
                .eq("delete_status", 1)
                ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        UserEntity userEntity = userService.selectOne(queryWrapper);
        if("".equals(user.getPhoto()) || "null".equals(user.getPhoto())){
            user.setPhoto(null);
        }
        if(userEntity==null){
            userService.updateById(user);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户、联系方式或身份证号已被使用");
        }
    }

    /**
     * 删除（逻辑删除）
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        ArrayList<UserEntity> list = new ArrayList<>();
        for(Integer id:ids){
            UserEntity userEntity = new UserEntity();
            userEntity.setId(id);
            userEntity.setDeleteStatus(2);
            list.add(userEntity);
        }
        if(list != null && list.size() >0){
            userService.updateBatchById(list);
        }
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer userId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<UserEntity> userList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("../../upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            UserEntity userEntity = new UserEntity();
                            userEntity.setUsername(data.get(0));                    //账户
                            userEntity.setPassword("123456");//密码
                            userEntity.setName(data.get(1));                    //用户/商家名称
                            userEntity.setPhone(data.get(2));                    //联系方式
                            userEntity.setIdNumber(data.get(3));                    //身份证号
                            userEntity.setPhoto("");//头像
                            userEntity.setSexTypes(data.size() > 4 ? Integer.valueOf(data.get(4)) : null);   //性别
                            userEntity.setEmail(data.size() > 5 ? data.get(5) : null);                    //邮箱
                            userEntity.setNewMoney(data.size() > 6 ? Double.valueOf(data.get(6)) : 0.00);                    //账户余额
                            userEntity.setContent(data.size() > 7 ? data.get(7) : null);                    //简介/备注
                            userEntity.setDeleteStatus(1);//逻辑删除字段
                            userEntity.setCreateTime(date);//时间
                            userList.add(userEntity);


                            //把要查询是否重复的字段放入map中
                            //账户
                            if(seachFields.containsKey("username")){
                                List<String> username = seachFields.get("username");
                                username.add(data.get(0));
                            }else{
                                List<String> username = new ArrayList<>();
                                username.add(data.get(0));
                                seachFields.put("username",username);
                            }
                            //联系方式
                            if(seachFields.containsKey("phone")){
                                List<String> phone = seachFields.get("phone");
                                phone.add(data.get(2));
                            }else{
                                List<String> phone = new ArrayList<>();
                                phone.add(data.get(2));
                                seachFields.put("phone",phone);
                            }
                            //身份证号
                            if(seachFields.containsKey("id_number")){
                                List<String> idNumber = seachFields.get("id_number");
                                idNumber.add(data.get(3));
                            }else{
                                List<String> idNumber = new ArrayList<>();
                                idNumber.add(data.get(3));
                                seachFields.put("id_number",idNumber);
                            }
                        }

                        //查询是否重复
                        //账户
                        List<UserEntity> userEntities_username = userService.selectList(new EntityWrapper<UserEntity>().in("username", seachFields.get("username")).eq("delete_status", 1));
                        if(userEntities_username.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(UserEntity s:userEntities_username){
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511,"数据库的该表中的 [账户] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        //联系方式
                        List<UserEntity> userEntities_phone = userService.selectList(new EntityWrapper<UserEntity>().in("phone", seachFields.get("phone")).eq("delete_status", 1));
                        if(userEntities_phone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(UserEntity s:userEntities_phone){
                                repeatFields.add(s.getPhone());
                            }
                            return R.error(511,"数据库的该表中的 [联系方式] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        //身份证号
                        List<UserEntity> userEntities_idNumber = userService.selectList(new EntityWrapper<UserEntity>().in("id_number", seachFields.get("id_number")).eq("delete_status", 1));
                        if(userEntities_idNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(UserEntity s:userEntities_idNumber){
                                repeatFields.add(s.getIdNumber());
                            }
                            return R.error(511,"数据库的该表中的 [身份证号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        userService.insertBatch(userList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }


    /**
     * 登录
     */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", username).eq("delete_status", 1));
        if(user==null || !user.getPassword().equals(password))
            return R.error("账号或密码不正确");
        String token = tokenService.generateToken(user.getId(), username, "user", "用户");
        R r = R.ok();
        r.put("token", token);
        r.put("role","用户");
        r.put("username", user.getName());
        r.put("tableName","user");
        r.put("userId", user.getId());
        return r;
    }

    /**
     * 注册
     */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody UserEntity user){
        Wrapper<UserEntity> queryWrapper = new EntityWrapper<UserEntity>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("id_number", user.getIdNumber())
                .andNew()
                .eq("delete_status", 1)
                ;
        UserEntity userEntity = userService.selectOne(queryWrapper);
        if(userEntity != null)
            return R.error("账户、联系方式或身份证号已被使用");
        user.setNewMoney(0.00);
        user.setDeleteStatus(1);
        user.setCreateTime(new Date());
        userService.insert(user);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        UserEntity user = new UserEntity();
        user.setPassword("123456");
        user.setId(id);
        userService.updateById(user);
        return R.ok();
    }


    /**
     * 忘记密码
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", username).eq("delete_status", 1));
        if(user!=null){
            user.setPassword("123456");
            boolean b = userService.updateById(user);
            if(!b){
                return R.error();
            }
        }else{
            return R.error("账号不存在");
        }
        return R.ok();
    }


    /**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        UserEntity user = userService.selectById(id);
        if(user !=null){
            //entity转view
            UserView view = new UserView();
            BeanUtils.copyProperties( user , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
     * 退出
     */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }




    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = userService.queryPage(params);

        //字典表数据转换
        List<UserView> list =(List<UserView>)page.getList();
        for(UserView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        UserEntity user = userService.selectById(id);
        if(user !=null){


            //entity转view
            UserView view = new UserView();
            BeanUtils.copyProperties( user , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody UserEntity user, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,user:{}",this.getClass().getName(),user.toString());
        Wrapper<UserEntity> queryWrapper = new EntityWrapper<UserEntity>()
                .eq("username", user.getUsername())
                .or()
                .eq("phone", user.getPhone())
                .or()
                .eq("id_number", user.getIdNumber())
                .andNew()
                .eq("delete_status", 1)
                ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        UserEntity userEntity = userService.selectOne(queryWrapper);
        if(userEntity==null){
            user.setDeleteStatus(1);
            user.setCreateTime(new Date());
            user.setPassword("123456");
            userService.insert(user);
            return R.ok();
        }else {
            return R.error(511,"账户、联系方式或身份证号已被使用");
        }
    }


}