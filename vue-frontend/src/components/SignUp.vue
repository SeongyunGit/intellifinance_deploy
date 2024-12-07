<template>
    <div class="signup-container">
      <h2>회원가입</h2>
      <form @submit.prevent="handleSignup">
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
        <div>
          <label>Nickname:</label>
          <input 
            type="text" 
            v-model="nickname" 
            required 
          />
        </div>
        <button type="submit">회원가입</button>
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
        nickname: '',
        error: null
      }
    },
    methods: {
      async handleSignup() {
        try {
          this.error = null;
          const response = await AuthService.signup(
            this.username, 
            this.password, 
            this.nickname
          );
          
          // 회원가입 성공 시 처리
          alert('회원가입 성공!');
          this.$router.push('/login');
        } catch (error) {
          // 에러 처리
          this.error = error.response?.data?.message || '회원가입 중 오류 발생';
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .signup-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
  }
  .error {
    color: red;
  }
  </style>