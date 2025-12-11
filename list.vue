<template>
  <div class="main-content">

    <!-- 条件查询 -->
    <div v-if="showFlag">
      <el-form :inline="true" :model="searchForm" class="form-content">
        <el-row :gutter="20" class="slt" :style="{justifyContent:contents.searchBoxPosition=='1'?'flex-start':contents.searchBoxPosition=='2'?'center':'flex-end'}">

          <el-form-item :label="contents.inputTitle == 1 ? '用户名称' : ''">
            <el-input prefix-icon="el-icon-search" v-model="searchForm.name" placeholder="用户名称" clearable></el-input>
          </el-form-item>



          <el-form-item>
            <el-button type="success" @click="search()">查询<i class="el-icon-search el-icon--right"/></el-button>
          </el-form-item>
        </el-row>
        <el-row class="ad" :style="{justifyContent:contents.btnAdAllBoxPosition=='1'?'flex-start':contents.btnAdAllBoxPosition=='2'?'center':'flex-end'}">
          <el-form-item>
            <el-button
                v-if="isAuth('user','新增')"
                type="success"
                icon="el-icon-plus"
                @click="addOrUpdateHandler()"
            >新增</el-button>
            &nbsp;
            <el-button
                v-if="isAuth('user','删除')"
                :disabled="dataListSelectionsns.length <= 0"
                type="danger"
                icon="el-icon-delete"
                @click="deleteHandler()"
            >删除</el-button>
            &nbsp;
            <el-button
                v-if="isAuth('user','报表')"
                type="success"
                icon="el-icon-pie-chart"
                @click="chartDialog()"
            >报表</el-button>
            &nbsp;
            <a style="text-decoration:none" class="el-button el-button--success"
               v-if="isAuth('user','导入导出')"
               icon="el-icon-download"
               href="http://localhost:8080/xiaoyuanershoujiaoyi/upload/userMuBan.xls"
            >批量导入用户数据模板</a>
            &nbsp;
            <el-upload
                v-if="isAuth('user','导入导出')"
                style="display: inline-block"
                action="xiaoyuanershoujiaoyi/file/upload"
                :on-success="userUploadSuccess"
                :on-error="userUploadError"
                :show-file-list = false>
              <el-button
                  v-if="isAuth('user','导入导出')"
                  type="success"
                  icon="el-icon-upload2"
              >批量导入用户数据</el-button>
            </el-upload>
            &nbsp;
            <!-- 导出excel -->
            <download-excel v-if="isAuth('user','导入导出')" style="display: inline-block" class = "export-excel-wrapper" :data = "dataList" :fields = "json_fields" name = "user.xls">
              <!-- 导出excel -->
              <el-button
                  type="success"
                  icon="el-icon-download"
              >导出</el-button>
            </download-excel>
            &nbsp;
          </el-form-item>
        </el-row>
      </el-form>
      <div class="table-content">
        <el-table class="tables" :size="contents.tableSize" :show-header="contents.tableShowHeader"
                  :header-row-style="headerRowStyle" :header-cell-style="headerCellStyle"
                  :border="contents.tableBorder"
                  :fit="contents.tableFit"
                  :stripe="contents.tableStripe"
                  :row-style="rowStyle"
                  :cell-style="cellStyle"
                  :style="{width: '100%',fontSize:contents.tableContentFontSize,color:contents.tableContentFontColor}"
                  v-if="isAuth('user','查看')"
                  :data="dataList"
                  v-loading="dataListLoading"
                  @selection-change="selectionChangeHandler">
          <el-table-column  v-if="contents.tableSelection"
                            type="selection"
                            header-align="center"
                            align="center"
                            width="50">
          </el-table-column>
          <el-table-column label="索引" v-if="contents.tableIndex" type="index" width="50" />

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="username"
                            header-align="center"
                            label="登录账号">
            <template slot-scope="scope">
              {{scope.row.username}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="name"
                            header-align="center"
                            label="用户名称">
            <template slot-scope="scope">
              {{scope.row.name}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="phone"
                            header-align="center"
                            label="联系方式">
            <template slot-scope="scope">
              {{scope.row.phone}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="idNumber"
                            header-align="center"
                            label="身份证号">
            <template slot-scope="scope">
              {{scope.row.idNumber}}
            </template>
          </el-table-column>
          <el-table-column :sortable="contents.tableSortable" :align="contents.tableAlign" prop="photo"
                           header-align="center"
                           width="200"
                           label="头像">
            <template slot-scope="scope">
              <div v-if="scope.row.photo">
                <img :src="scope.row.photo" width="100" height="100">
              </div>
              <div v-else>无图片</div>
            </template>
          </el-table-column>
          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="sexTypes"
                            header-align="center"
                            label="性别">
            <template slot-scope="scope">
              {{scope.row.sexValue}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="email"
                            header-align="center"
                            label="邮箱">
            <template slot-scope="scope">
              {{scope.row.email}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="newMoney"
                            header-align="center"
                            label="账户余额">
            <template slot-scope="scope">
              {{scope.row.newMoney}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="content"
                            header-align="center"
                            label="简介/备注">
            <template slot-scope="scope">
              {{scope.row.content}}
            </template>
          </el-table-column>

          <el-table-column  :sortable="contents.tableSortable" :align="contents.tableAlign"
                            prop="deleteStatus"
                            header-align="center"
                            label="状态">
            <template slot-scope="scope">
              {{scope.row.deleteStatus === 1 ? '正常' : '已删除'}}
            </template>
          </el-table-column>

          <el-table-column width="300" :align="contents.tableAlign"
                           header-align="center"
                           label="操作">
            <template slot-scope="scope">
              <el-button v-if="isAuth('user','查看')" type="success" icon="el-icon-tickets" size="mini" @click="addOrUpdateHandler(scope.row.id,'info')">详情</el-button>
              <el-button v-if="isAuth('user','修改')" type="primary" icon="el-icon-edit" size="mini" @click="addOrUpdateHandler(scope.row.id)">修改</el-button>

              <el-button v-if="isAuth('user','删除')" type="danger" icon="el-icon-delete" size="mini" @click="deleteHandler(scope.row.id)">删除</el-button>

              <el-button v-if="isAuth('user','修改')" type="success" icon="el-icon-tickets" size="mini" @click="resetPassword(scope.row.id)">重置密码</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            clsss="pages"
            :layout="layouts"
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageIndex"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="Number(contents.pageEachNum)"
            :total="totalPage"
            :small="contents.pageStyle"
            class="pagination-content"
            :background="contents.pageBtnBG"
            :style="{textAlign:contents.pagePosition==1?'left':contents.pagePosition==2?'center':'right'}"
        ></el-pagination>
      </div>
    </div>
    <!-- 添加/修改页面  将父组件的search方法传递给子组件-->
    <add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>



    <el-dialog title="统计报表" :visible.sync="chartVisiable" width="800">
      <el-date-picker
          v-model="echartsDate"
          type="year"
          placeholder="选择年">
      </el-date-picker>
      <el-button @click="chartDialog()">查询</el-button>
      <div id="statistic" style="width:100%;height:600px;"></div>
      <span slot="footer" class="dialog-footer">
				<el-button @click="chartVisiable = false">关闭</el-button>
			</span>
    </el-dialog>

  </div>
</template>
<script>
import AddOrUpdate from "./add-or-update";
import styleJs from "../../../utils/style.js";

export default {
  data() {
    return {
      searchForm: {
        key: ""
      },
      sessionTable : "",//登录账户所在表名
      role : "",//权限
      userId:"",//当前登录人的id
      //级联表下拉框搜索条件
      //当前表下拉框搜索条件
      form:{
        id : null,
        username : null,
        password : null,
        name : null,
        phone : null,
        idNumber : null,
        photo : null,
        sexTypes : null,
        email : null,
        newMoney : null,
        content : null,
        deleteStatus : null,
        createTime : null,
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      showFlag: true,
      sfshVisiable: false,
      shForm: {},
      chartVisiable: false,
      echartsDate: new Date(),//echarts的时间查询字段
      addOrUpdateFlag:false,
      contents:null,
      layouts: '',

      //导出excel
      json_fields: {
        //级联表字段
        //本表字段
        '登录账号': "username",
        '用户名称': "name",
        '联系方式': "phone",
        '身份证号': "idNumber",
        '头像': "photo",
        '性别': "sexTypes",
        '邮箱': "email",
        '账户余额': "newMoney",
        '简介/备注': "content",
        '状态': "deleteStatus",
      },

    };
  },
  created() {
    this.contents = styleJs.listStyle();
    this.init();
    this.getDataList();
    this.contentStyleChange()
  },
  mounted() {
    //获取当前登录用户的信息
    this.sessionTable = this.$storage.get("sessionTable");
    this.role = this.$storage.get("role");
    this.userId = this.$storage.get("userId");

  },
  filters: {
    htmlfilter: function (val) {
      return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
    }
  },
  components: {
    AddOrUpdate,
  },
  computed: {
  },
  methods: {
    chartDialog() {
      let _this = this;
      let params = {
        dateFormat :"%Y", //%Y-%m
        riqi :_this.echartsDate.getFullYear(),
        // riqi :_this.echartsDate.getFullYear()+"-"+(_this.echartsDate.getMonth() + 1 < 10 ? '0' + (_this.echartsDate.getMonth() + 1) : _this.echartsDate.getMonth() + 1),
        thisTable : {//当前表
          tableName :'user',//当前表表名,
          sumColum : 'user_number', //求和字段
          date : 'create_time',//分组日期字段
          // string : 'name',//分组字符串字段
          // types : 'sex_types',//分组下拉框字段
        },
      }
      _this.chartVisiable = true;
      _this.$nextTick(() => {
        var statistic = this.$echarts.init(document.getElementById("statistic"), 'macarons');
        this.$http({
          url: "barSum",
          method: "get",
          params: params
        }).then(({data}) => {
          if(data && data.code === 0){

            //柱状图 求和 已成功使用
            //start
            let yAxisName = "数值";//根据查询数据具体改(单列要改,多列不改)
            let xAxisName = "月份";
            let series = [];//具体数据值
            data.data.yAxis.forEach(function (item,index) {
              let tempMap = {};
              tempMap.name=data.data.legend[index];
              tempMap.type='bar';
              tempMap.data=item;
              series.push(tempMap);

            })

            var option = {
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'cross',
                  crossStyle: {
                    color: '#999'
                  }
                }
              },
              toolbox: {
                feature: {
                  magicType: { show: true, type: ['line', 'bar'] },
                  saveAsImage: { show: true }
                }
              },
              legend: {
                data: data.data.legend
              },
              xAxis: [
                {
                  type: 'category',
                  name: xAxisName,
                  data: data.data.xAxis,
                  axisPointer: {
                    type: 'shadow'
                  }
                }
              ],
              yAxis: [
                {
                  type: 'value',
                  name: yAxisName,
                  axisLabel: {
                    formatter: '{value}'
                  }
                }
              ],
              series:series
            };
            statistic.setOption(option,true);
            window.onresize = function () {
              statistic.resize();
            };
            //end
          }else {
            this.$message({
              message: "报表未查询到数据",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.search();
              }
            });
          }
        });
      });
    },
    // 初始化方法
    init() {
      // 初始化分页布局
      this.layouts = this.contents.pageLayout == 1 ? "total, sizes, prev, pager, next, jumper" :
          this.contents.pageLayout == 2 ? "prev, pager, next" :
              this.contents.pageLayout == 3 ? "total, prev, pager, next" :
                  "total, sizes, prev, pager, next, jumper";
    },
    // 表格样式变化
    contentStyleChange() {
      this.contents = styleJs.listStyle();
      this.init();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: "/user/page",
        method: "get",
        params: {
          page: this.pageIndex,
          limit: this.pageSize,
          ...this.searchForm
        }
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.total;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      });
    },
    // 分页大小改变
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页码改变
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 搜索
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },
    // 选择改变
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    // 添加/修改
    addOrUpdateHandler(id) {
      this.addOrUpdateFlag = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandler(id) {
      var ids = id ? [id] : this.dataListSelections.map(item => item.id);
      this.$confirm(`确定要删除选中的${ids.length}条数据吗?`, "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "/user/delete",
          method: "post",
          data: ids
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: "删除成功",
              type: "success",
              duration: 1500,
              onClose: () => {
                this.getDataList();
              }
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 重置密码
    resetPassword(id) {
      this.$confirm("确定要重置该用户密码为123456吗?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.$http({
          url: "/user/resetPassword",
          method: "post",
          data: {id: id}
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: "重置密码成功",
              type: "success"
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      });
    },
    // 上传成功
    userUploadSuccess(response) {
      if (response.code === 0) {
        this.$message({
          message: "导入成功",
          type: "success",
          duration: 1500,
          onClose: () => {
            this.getDataList();
          }
        });
      } else {
        this.$message.error(response.msg);
      }
    },
    // 上传失败
    userUploadError() {
      this.$message.error("导入失败");
    }
  }
}
</script>