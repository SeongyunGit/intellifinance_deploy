<template>
  <div class="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
  <div class="max-w-lg w-full space-y-8">
    <div class="bg-white shadow-lg rounded-lg p-8">
      <div class="text-center">
        <h2 class="text-2xl font-bold text-gray-900">Create an Account</h2>
        <p class="mt-2 text-sm text-gray-600">Join us and start your journey today!</p>
      </div>
      <form class="mt-8 space-y-6" @submit.prevent="signUp">
        <!-- Username Input -->
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">Username</label>
          <div class="mt-1">
            <input type="username" id="username" v-model.trim="username" required
              class="appearance-none rounded-md relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-400 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="Your username">
          </div>
        </div>

        <!-- Email Input -->
        <div>
          <label for="nickname" class="block text-sm font-medium text-gray-700">Nickname</label>
          <div class="mt-1">
            <input type="nickname" id="nickname" v-model.trim="nickname" required
              class="appearance-none rounded-md relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-400 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="nickname">
          </div>
        </div>

        <!-- Password Input -->
        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
          <div class="mt-1">
            <input type="password" id="password" v-model.trim="password" required
              class="appearance-none rounded-md relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-400 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="••••••••">
          </div>
        </div>

        <!-- Confirm Password Input -->
        <div>
          <label for="password2" class="block text-sm font-medium text-gray-700">Confirm Password</label>
          <div class="mt-1">
            <input type="password" id="password2" v-model.trim="password2" required
              class="appearance-none rounded-md relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-400 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="••••••••">
          </div>
        </div>

        <!-- Submit Button -->
        <div>
          <button type="submit" value="SignUp"
            class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
            Sign Up
          </button>
        </div>
      </form>
    </div>

    <!-- Login Redirect -->
    <p class="mt-6 text-center text-sm text-gray-600">
      Already have an account? 
      <a href="/login" class="font-medium text-indigo-600 hover:text-indigo-500">Log in</a>
    </p>
  </div>
</div>
   
</template>

<script setup>
import { ref } from 'vue'
import { useCounterStore } from '@/stores/counter'

const username = ref(null)
const nickname = ref(null)
const password = ref(null)
const password2 = ref(null)

const store = useCounterStore()

const signUp = function () {
  const payload = {
    username: username.value,
    nickname: nickname.value,
    password: password.value,
    password2: password2.value,
  }
  console.log(payload)
  
  store.signUp(payload)
}

</script>

<style>

</style>
