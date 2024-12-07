<template>
    <div class="login-container">
      <h2>로그인</h2>
      <form @submit.prevent="handleLogin">
        <div>
          <label>Username:</label>
          <input 
            type="text" 
            v-model="username" 
            required 
          />
        </div>
        <div>
          <label>Password:</label>
          <input 
            type="password" 
            v-model="password" 
            required 
          />
        </div>
        <button type="submit">로그인</button>
      </form>
      <p v-if="error" class="error">{{ error }}</p>
    </div>
  </template>
  
  <script>
  import AuthService from '@/services/authService';
  
  export default {
    data() {
      return {
        username: '',
        password: '',
        error: null
      }
    },
    methods: {
      async handleLogin() {
        try {
          this.error = null;
          const user = await AuthService.login(
            this.username, 
            this.password
          );
          
          // 로그인 성공 시 처리
          alert('로그인 성공!');
          this.$router.push('/');  // 홈 화면으로 이동
        } catch (error) {
          // 에러 처리
          this.error = error.response?.data?.message || '로그인 중 오류 발생';
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .login-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
  }
  .error {
    color: red;
  }
  </style>