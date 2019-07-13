<template>
  <div class="login-box">
    <ul v-if="flag">
      <button class="login-btn" @click="dialogFormVisible = true">登录/注册</button>
    </ul>
    <ul v-if="!flag">
      <span class="span-text" :visible.sync="dialogExitVisible">{{this.userInfo}}</span>
      <avatar class="avatar" src="https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541280047.jpg" :rounded="false"></avatar>
      <button class="exit-btn" :visible.sync="dialogExitVisible" @click="logout">退出</button>
    </ul>
    <el-dialog title="登录" :visible.sync="dialogFormVisible" center :append-to-body='true' :lock-scroll="false"
               width="25%">


      <el-form ref="form" :model="form" size="small" label-width="100px">
        <el-row>
          <el-form-item label="用户名：" style="width: 350px">
            <el-input class="search-input" v-model="form.id"/>
          </el-form-item>
          <el-form-item label="密码：" style="width: 350px">
            <el-input type="password" class="search-input" v-model="form.pw"/>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">

        <el-button type="primary" @click="login">登陆</el-button>
      </div>


    </el-dialog>

  </div>


</template>

<script>
  export default {
    name: "LoginBox"
  }
</script>

<style lang="less" scoped>
  .login-btn {
    box-sizing: border-box;
    position: absolute;
    top: 30px;
    right: 60px;
    min-width: 50px;
    width: 80px;
    height: 50%;
    line-height: 1;
    color: beige;
    border: none;
    border-color: cornflowerblue;
    background-color: #f45a8d;
    border-radius: 0 2px 2px 0;
    text-align: center;
    line-height: 42px;
    font-family: PingFangSC-Medium;
  }

  .exit-btn {
    box-sizing: border-box;
    position: absolute;
    top: 30px;
    right: 60px;
    min-width: 50px;
    width: 80px;
    height: 50%;
    line-height: 1;
    color: beige;
    border: none;
    border-color: cornflowerblue;
    background-color: #f45a8d;
    border-radius: 0 2px 2px 0;
    text-align: center;
    line-height: 42px;
    font-family: PingFangSC-Medium;


  }

  .span-text {
    font-size: 14px;
    color: #4d4d4d;
    font-weight: 700;
    top: 43px;
    position: absolute;
    padding-left: 100px;
    right: 180px;
  }
  .avatar {
    top: 29px;
    position: absolute;
    right: 240px;
    color: black;
  }


</style>


<script>

  import Avatar from 'vue-avatar'

  export default {
    components: {
      Avatar
    },

    data() {

      return {
        dialogExitVisible: false,
        token: '',
        dialogFormVisible: false,
        flag: false,
        showname: false,
        userInfo: '',
        form: {
          id: '',
          pw: '',
        },

      };
    },
    created() {
      this.userInfo = window.localStorage.getItem("name")
    },


    methods: {
      //登录
      login: function () {
        var name = this.form.id;
        var password = this.form.pw;
        console.log(name);
        console.log(password);
        if (name == '' || password == '') {
          this.$message({
            message: '账号或密码为空！',
            type: 'error'
          })
          return;
        }
        this.axios.get("user/login", {params: {'username': this.form.id, 'password': this.form.pw}}).then(response => {

          if (response.data.code == "200") {
            this.$message({
              message: "登录成功",
              type: 'error'
            })
            this.dialogFormVisible = false;
            this.token = response.data.data;
            console.log(this.token);
            this.showname = true;
            if (this.showname) {
              this.axios.get(`user/token/${this.token}`).then(response => {
                console.log(response.data.data.doubanUserNickname)
                console.log(JSON.stringify(response.data.data))
                localStorage.setItem('name', response.data.data.doubanUserNickname);
                localStorage.setItem('id', response.data.data.id);
                localStorage.setItem('url', response.data.data.doubanUserAvatar);
                localStorage.setItem('Flag', true);
                this.flag = localStorage.getItem('Flag');
                // this.$router.push('/movie/home')
                this.$router.go(0)
              })
            }

          }
          console.log(this.showname);

          if (response.data.code == "400") {
            this.$message({
              message: response.data.msg,
              type: 'error'
            })
          }
        })


      },
      logout: function () {
        localStorage.setItem('Flag', false);
        this.flag = localStorage.getItem('Flag');
      }
    }

  };
</script>
