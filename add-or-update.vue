<template>
  <div class="addEdit-block">
    <el-form
        class="detail-form-content"
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="80px"
        :style="{backgroundColor:addEditForm.addEditBoxColor}">
      <el-row>
        <input id="updateId" name="id" type="hidden">
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'"  label="登录账号" prop="username">
            <el-input v-model="ruleForm.username"
                      placeholder="登录账号" clearable  :readonly="ro.username"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="登录账号" prop="username">
              <el-input v-model="ruleForm.username"
                        placeholder="登录账号" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'"  label="用户名称" prop="name">
            <el-input v-model="ruleForm.name"
                      placeholder="用户名称" clearable  :readonly="ro.name"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="用户名称" prop="name">
              <el-input v-model="ruleForm.name"
                        placeholder="用户名称" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'"  label="联系方式" prop="phone">
            <el-input v-model="ruleForm.phone"
                      placeholder="联系方式" clearable  :readonly="ro.phone"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="联系方式" prop="phone">
              <el-input v-model="ruleForm.phone"
                        placeholder="联系方式" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'"  label="身份证号" prop="idNumber">
            <el-input v-model="ruleForm.idNumber"
                      placeholder="身份证号" clearable  :readonly="ro.idNumber"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="身份证号" prop="idNumber">
              <el-input v-model="ruleForm.idNumber"
                        placeholder="身份证号" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="24">
          <el-form-item class="upload" v-if="type!='info' && !ro.photo" label="头像" prop="photo">
            <file-upload
                tip="点击上传头像"
                action="file/upload"
                :limit="3"
                :multiple="true"
                :fileUrls="ruleForm.photo?ruleForm.photo:''"
                @change="photoUploadChange"
            ></file-upload>
          </el-form-item>
          <div v-else>
            <el-form-item v-if="ruleForm.photo" label="头像" prop="photo">
              <img style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in (ruleForm.photo || '').split(',')" :src="item" width="100" height="100">
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="select" v-if="type!='info'"  label="性别" prop="sexTypes">
            <el-select v-model="ruleForm.sexTypes" :disabled="ro.sexTypes" placeholder="请选择性别">
              <el-option
                  v-for="(item,index) in sexTypesOptions"
                  v-bind:key="item.codeIndex"
                  :label="item.indexName"
                  :value="item.codeIndex">
              </el-option>
            </el-select>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="性别" prop="sexValue">
              <el-input v-model="ruleForm.sexValue"
                        placeholder="性别" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'"  label="邮箱" prop="email">
            <el-input v-model="ruleForm.email"
                      placeholder="邮箱" clearable  :readonly="ro.email"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="邮箱" prop="email">
              <el-input v-model="ruleForm.email"
                        placeholder="邮箱" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="12">
          <el-form-item class="input" v-if="type!='info'"  label="账户余额" prop="newMoney">
            <el-input v-model="ruleForm.newMoney"
                      placeholder="账户余额" clearable  :readonly="ro.newMoney"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="账户余额" prop="newMoney">
              <el-input v-model="ruleForm.newMoney"
                        placeholder="账户余额" readonly></el-input>
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="24">
          <el-form-item class="input" v-if="type!='info'"  label="简介/备注" prop="content">
            <el-input v-model="ruleForm.content"
                      placeholder="简介/备注" clearable  :readonly="ro.content" type="textarea"></el-input>
          </el-form-item>
          <div v-else>
            <el-form-item class="input" label="简介/备注" prop="content">
              <el-input v-model="ruleForm.content"
                        placeholder="简介/备注" readonly type="textarea"></el-input>
            </el-form-item>
          </div>
        </el-col>
      </el-row>
      <el-form-item class="btn">
        <el-button v-if="type!='info'" type="primary" class="btn-success" @click="onSubmit">提交</el-button>
        <el-button v-if="type!='info'" class="btn-close" @click="back()">取消</el-button>
        <el-button v-if="type=='info'" class="btn-close" @click="back()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import styleJs from "../../../utils/style.js";
// 数字，邮件，手机，url，身份证校验
import { isNumber,isIntNumer,isEmail,isPhone, isMobile,isURL,checkIdCard } from "@/utils/validate";
export default {
  data() {
    return {
      addEditForm:null,
      id: '',
      type: '',
      sessionTable : "",//登录账户所在表名
      role : "",//权限
      userId:"",//当前登录人的id
      ro:{
        username: false,
        password: false,
        name: false,
        phone: false,
        idNumber: false,
        photo: false,
        sexTypes: false,
        email: false,
        newMoney: false,
        content: false,
      },
      ruleForm: {
        username: '',
        password: '123456',
        name: '',
        phone: '',
        idNumber: '',
        photo: '',
        sexTypes: '',
        email: '',
        newMoney: '0.00',
        content: '',
      },
      sexTypesOptions : [],
      rules: {
        username: [
          { required: true, message: '登录账号不能为空', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
        ],
        phone: [
          {  required: true, message: '联系方式不能为空', trigger: 'blur' },
          {  pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
            message: '联系方式格式不对',
            trigger: 'blur'
          }
        ],
        idNumber: [
          { required: false, message: '身份证号不能为空', trigger: 'blur' },
          { pattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
            message: '身份证号格式有误！',
            trigger: 'blur'
          }
        ],
        photo: [
          { required: false, message: '头像不能为空', trigger: 'blur' },
        ],
        sexTypes: [
          { required: false, message: '性别不能为空', trigger: 'blur' },
          {  pattern: /^[1-9][0-9]*$/,
            message: '只允许输入整数',
            trigger: 'blur'
          }
        ],
        email: [
          { required: false, message: '邮箱不能为空', trigger: 'blur' },
          { pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
            message: '邮箱只能是邮箱格式',
            trigger: 'blur'
          }
        ],
        newMoney: [
          { required: true, message: '账户余额不能为空', trigger: 'blur' },
          {  pattern: /^[0-9]{0,6}(\.[0-9]{1,2})?$/,
            message: '只允许输入整数6位,小数2位的数字',
            trigger: 'blur'
          }
        ],
      }
    };
  },
  props: ["parent"],
  computed: {
  },
  created() {
    //获取当前登录用户的信息
    this.sessionTable = this.$storage.get("sessionTable");
    this.role = this.$storage.get("role");
    this.userId = this.$storage.get("userId");


    if (this.role != "管理员"){
    }
    this.addEditForm = styleJs.addStyle();
    this.addEditStyleChange()
    this.addEditUploadStyleChange()
    //获取下拉框信息
    this.$http({
      url:`dictionary/page?page=1&limit=100&sort=&order=&dicCode=sex_types`,
      method: "get"
    }).then(({ data }) => {
      if (data && data.code === 0) {
        this.sexTypesOptions = data.data.list;
      }
    });


  },
  mounted() {
  },
  methods: {
    // 下载
    download(file){
      window.open(`${file}`)
    },
    // 初始化
    init(id,type) {
      if (id) {
        this.id = id;
        this.type = type;
      }
      if(this.type=='info'||this.type=='else'){
        this.info(id);
      }
      // 获取用户信息
      this.$http({
        url:`${this.$storage.get("sessionTable")}/session`,
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          var json = data.data;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    // 多级联动参数
    info(id) {
      let _this =this;
      _this.$http({
        url: `user/info/${id}`,
        method: 'get'
      }).then(({ data }) => {
        if (data && data.code === 0) {
          _this.ruleForm = data.data;
        } else {
          _this.$message.error(data.msg);
        }
      });
    },
    // 提交
    onSubmit() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          this.$http({
            url:`user/${!this.ruleForm.id ? "save" : "update"}`,
            method: "post",
            data: this.ruleForm
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.parent.showFlag = true;
                  this.parent.addOrUpdateFlag = false;
                  this.parent.userCrossAddOrUpdateFlag = false;
                  this.parent.search();
                  this.parent.contentStyleChange();
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    },
    // 获取uuid
    getUUID () {
      return new Date().getTime();
    },
    // 返回
    back() {
      this.parent.showFlag = true;
      this.parent.addOrUpdateFlag = false;
      this.parent.userCrossAddOrUpdateFlag = false;
      this.parent.contentStyleChange();
    },
    //图片
    photoUploadChange(fileUrls){
      this.ruleForm.photo = fileUrls;
      this.addEditUploadStyleChange()
    },

    addEditStyleChange() {
      this.$nextTick(()=>{
        // input
        document.querySelectorAll('.addEdit-block .input .el-input__inner').forEach(el=>{
          el.style.height = this.addEditForm.inputHeight
          el.style.color = this.addEditForm.inputFontColor
          el.style.fontSize = this.addEditForm.inputFontSize
          el.style.borderWidth = this.addEditForm.inputBorderWidth
          el.style.borderStyle = this.addEditForm.inputBorderStyle
          el.style.borderColor = this.addEditForm.inputBorderColor
          el.style.borderRadius = this.addEditForm.inputBorderRadius
          el.style.backgroundColor = this.addEditForm.inputBgColor
        })
        document.querySelectorAll('.addEdit-block .input .el-form-item__label').forEach(el=>{
          el.style.lineHeight = this.addEditForm.inputHeight
          el.style.color = this.addEditForm.inputLableColor
          el.style.fontSize = this.addEditForm.inputLableFontSize
        })
        // select
        document.querySelectorAll('.addEdit-block .select .el-input__inner').forEach(el=>{
          el.style.height = this.addEditForm.selectHeight
          el.style.color = this.addEditForm.selectFontColor
          el.style.fontSize = this.addEditForm.selectFontSize
          el.style.borderWidth = this.addEditForm.selectBorderWidth
          el.style.borderStyle = this.addEditForm.selectBorderStyle
          el.style.borderColor = this.addEditForm.selectBorderColor
          el.style.borderRadius = this.addEditForm.selectBorderRadius
          el.style.backgroundColor = this.addEditForm.selectBgColor
        })
        document.querySelectorAll('.addEdit-block .select .el-form-item__label').forEach(el=>{
          el.style.lineHeight = this.addEditForm.selectHeight
          el.style.color = this.addEditForm.selectLableColor
          el.style.fontSize = this.addEditForm.selectLableFontSize
        })
        document.querySelectorAll('.addEdit-block .select .el-select__caret').forEach(el=>{
          el.style.color = this.addEditForm.selectIconFontColor
          el.style.fontSize = this.addEditForm.selectIconFontSize
        })
        // date
        document.querySelectorAll('.addEdit-block .date .el-input__inner').forEach(el=>{
          el.style.height = this.addEditForm.dateHeight
          el.style.color = this.addEditForm.dateFontColor
          el.style.fontSize = this.addEditForm.dateFontSize
          el.style.borderWidth = this.addEditForm.dateBorderWidth
          el.style.borderStyle = this.addEditForm.dateBorderStyle
          el.style.borderColor = this.addEditForm.dateBorderColor
          el.style.borderRadius = this.addEditForm.dateBorderRadius
          el.style.backgroundColor = this.addEditForm.dateBgColor
        })
        document.querySelectorAll('.addEdit-block .date .el-form-item__label').forEach(el=>{
          el.style.lineHeight = this.addEditForm.dateHeight
          el.style.color = this.addEditForm.dateLableColor
          el.style.fontSize = this.addEditForm.dateLableFontSize
        })
        document.querySelectorAll('.addEdit-block .date .el-input__icon').forEach(el=>{
          el.style.color = this.addEditForm.dateIconFontColor
          el.style.fontSize = this.addEditForm.dateIconFontSize
          el.style.lineHeight = this.addEditForm.dateHeight
        })
        // upload
        let iconLineHeight = parseInt(this.addEditForm.uploadHeight) - parseInt(this.addEditForm.uploadBorderWidth) * 2 + 'px'
        document.querySelectorAll('.addEdit-block .upload .el-upload--picture-card').forEach(el=>{
          el.style.width = this.addEditForm.uploadHeight
          el.style.height = this.addEditForm.uploadHeight
          el.style.borderWidth = this.addEditForm.uploadBorderWidth
        })
      })
    },
    addEditUploadStyleChange() {
      this.$nextTick(()=>{
        // upload
        let iconLineHeight = parseInt(this.addEditForm.uploadHeight) - parseInt(this.addEditForm.uploadBorderWidth) * 2 + 'px'
        document.querySelectorAll('.addEdit-block .upload .el-upload--picture-card').forEach(el=>{
          el.style.width = this.addEditForm.uploadHeight
          el.style.height = this.addEditForm.uploadHeight
          el.style.borderWidth = this.addEditForm.uploadBorderWidth
          el.style.borderStyle = this.addEditForm.uploadBorderStyle
          el.style.borderColor = this.addEditForm.uploadBorderColor
          el.style.borderRadius = this.addEditForm.uploadBorderRadius
          el.style.backgroundColor = this.addEditForm.uploadBgColor
        })
        document.querySelectorAll('.addEdit-block .upload .el-icon-plus').forEach(el=>{
          el.style.lineHeight = iconLineHeight
          el.style.color = this.addEditForm.uploadIconFontColor
          el.style.fontSize = this.addEditForm.uploadIconFontSize
        })
        document.querySelectorAll('.addEdit-block .upload .el-upload__tip').forEach(el=>{
          el.style.color = this.addEditForm.uploadTipFontColor
          el.style.fontSize = this.addEditForm.uploadTipFontSize
        })
      })
    }
  },
  watch: {
    'addEditForm'(newVal,oldVal){
      this.addEditStyleChange()
      this.addEditUploadStyleChange()
    }
  }
};
</script>