import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import { useRouter } from 'vue-router'

export const useCounterStore = defineStore('counter', () => {
  const companyList = ref([])
  const companyListOption = ref([])
  const integrationProducts = ref([])
  const integrationProductOptions = ref([])

  const API_URL = 'http://127.0.0.1:8080/api'
  
  // 설문 초기값
  const initialSurveyData = {
    'id': 0,
    'user': null,
    'type_a': null,
    // 'today' # auto_now=True
    // 'fin_co_no': None,
    'kor_co_nm': [],  // 은행이름
    // 'intr_rate_type': None,
    'intr_rate_type_nm': [],  // 이자율(단리,복리)
    'save_trm': [],  // 저축기간
    'intr_rate': null,  // 기본금리
    'intr_rate2': null,  // 우대금리
    // 'rsrv_type': None,
    'rsrv_type_nm': [],  // 적립식종류
    // 'rpay_type': None,
    'rpay_type_nm': [],  // 상환방식
    // 'lend_rate_type': None,
    'lend_rate_type_nm': [],  // 금리유형
    'lend_rate_min': null,  // 최소 금리
    'lend_rate_max': null,  // 최대 금리
    'lend_rate_avg': null,  // 평균 금리
    // 'mrtg_type': None,
    'mrtg_type_nm': []  // 담보 유형
  }
  
  // surveyData 객체
  const surveyData = ref({
    'deposit' : { ...initialSurveyData },
    'saving' : { ...initialSurveyData },
    'mortgageLoan' : { ...initialSurveyData },
    'rentHouseLoan' : { ...initialSurveyData },
  });
  const type_a_4 = ['deposit', 'saving', 'mortgageLoan', 'rentHouseLoan']

  const mPK = ref()
  const token = ref(null)
  const Uname = ref('name')
  const Uemail = ref('abc@abcd.com')
  const isAdmin = ref(0)
  const isLogin = computed(() => {
    if (token.value === null) {
      return false
    } else {
      return true
    }
  })
  const router = useRouter()

  // 회원가입 요청 액션
  const signUp = function (payload) {

    let { username,nickname, password , password2 } = payload
    console.log({ username, nickname, password, password2 })
    
    axios({
      method: 'post',
      url: `${API_URL}/signup`,
      data: {
        username, nickname, password
      }
    })
      .then((res) => {
        // console.log(res)
        console.log('회원가입 성공')
        const password = password2

        logIn({ username, password })
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 로그인 요청 액션
  const logIn = function (payload) {
    const { username, password } = payload
    axios({
      method: 'post',
      url: `${API_URL}/login`,  // 로그인 API
      withCredentials: true,  // 쿠키와 함께 전송
      data: {
        username: username,
        password: password
      }
    })
      .then((res) => {
        console.log(res.data)
        token.value = res.data
        mPK.value = res.data.user_pk
        console.log(mPK.value)
        Uname.value = res.data.username
        console.log(Uname.value)
        Uemail.value = res.data.email
        console.log(Uemail.value)
        isAdmin.value = res.data.is_staff  // 관리자 여부 저장

        // type_a_4.forEach(item => getSurveyData(mPK.value, item))
        
        router.push({ name: 'HomeView' })
        console.log('로그인 성공')
      })
      .catch((err) => {
        console.log(err)
      }
    )
  }
  
  // [추가기능] 로그아웃
  const logOut = function () {
    axios({
      method: 'post',
      url: `${API_URL}/logout`,
    })
      .then((res) => {
        localStorage.removeItem('token'); 
        document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";  // 쿠
        token.value = null
        mPK.value = null
        Uname.value = 'name'
        Uemail.value = 'abc@abcd.com'
        isAdmin.value = 0
        surveyData.value = {
          'deposit' : { ...initialSurveyData },
          'saving' : { ...initialSurveyData },
          'mortgageLoan' : { ...initialSurveyData },
          'rentHouseLoan' : { ...initialSurveyData },
        };  // 초기 상태로 되돌리기
        router.push({ name: 'HomeView' })
      })
      .catch((err) => {
        console.log(err)
      })
  }

  const createSurvey = function (typea) {
    surveyData.value[typea].type_a = typea
    // console.log(token.value)
    // console.log('확인용', surveyData.value)
    axios({
      method: 'post',
      url: `${API_URL}/accounts/survey/start/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
      data: surveyData.value[typea],
    }
  )
    .then((response) => {
      surveyData.value[typea] = response.data
    })
    .catch((err) => {
      console.log(err)
    })
  }

  
  // api요청
  const getCompany = function () {
    axios({
      method: 'get',
      url: `${API_URL}/companies/all`,
    })
      .then((response) => {
        console.log(response.data)
        companyList.value = response.data
        
      })
      .catch((err) => {
        console.log(err)
      })
  }
  // api요청
  const getIntegration = function () {
    axios({
      method: 'get',
      url: `${API_URL}/bank/bank`,
    })
      .then((response) => {
        console.log(response.data)
        integrationProducts.value = response.data
      })
      .catch((err) => {
        console.log(err)
      })
  }
  // api요청
  const getcompany2 = function () {
    axios({
      method: 'get',
      url: `${API_URL}/api/v1/company/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }
  // api요청
  const getdeposit = function () {
    axios({
      method: 'get',
      url: `${API_URL}/api/v1/deposit/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }
  // api요청
  const getsaving = function () {
    axios({
      method: 'get',
      url: `${API_URL}/api/v1/saving/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 데이터베이스 요청
  const getmortgageLoan = function () {
    axios({
      method: 'get',
      url: `${API_URL}/api/v1/mortgageLoan/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }
  // 데이터베이스 요청
  const getrentHouseLoan = function () {
    axios({
      method: 'get',
      url: `${API_URL}/api/v1/rentHouseLoan/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 데이터베이스 초기화
  const delete_data = function () {
    axios({
      method: 'post',
      url: `${API_URL}/api/v1/delete_product_data/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // survey 데이터 가져오는 함수
  const getSurveyData = function (user_id, type) {
    axios({
      method: 'get',
      url: `${API_URL}/accounts/survey/${user_id}/${type}`,  // survey 데이터를 가져올 API endpoint
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        const survey = response.data
        console.log(survey)
        if (!survey) {
          console.log('Survey생성',survey)
          createSurvey(type)
        } else {
          console.log('Survey확인',survey)
          surveyData.value[type] = survey.surveyData
        }
        console.log('Survey Data Loaded:', surveyData.value)
      })
      .catch((err) => {
        console.log('Error loading survey data:', err)
      })
  }

  // survey 데이터 수정 함수 (PUT 요청)
  const updateSurveyData = function (SurveyId, updatedData, typea) {
  axios({
    method: 'put',
    url: `${API_URL}/accounts/survey/${SurveyId}/`,  // 특정 surveyId에 해당하는 URL로 PUT 요청
    data: updatedData,  // 수정할 데이터
  })
    .then((response) => {
      console.log('Survey Data Updated:', response.data)
      surveyData.value[typea] = response.data  // 수정된 데이터를 업데이트
    })
    .catch((err) => {
      console.log('Error updating Survey data:', err)
    })
}

  //공지사항
  const announcements = ref({})
  const getAnnouncementData = function () {
    
    axios({
      method: 'get',
      url: `${API_URL}/accounts/announcement/`,
    })
      .then((response) => {
        announcements.value = response.data
        console.log(announcements.value)
      })
      .catch((err) => {
        console.log(err)
      })
  }
  
  //은행 타입
  const selected = ref(0)
  const checkType = function(sel) {
    selected.value = sel
  }
  
  let bankId = 0
  const is_liked = ref({})
  const toggleLike = function (bank) {
    axios({
      method:'post',
      url: `${API_URL}/api/v1/api/bank/${bank}/like/`,
      withCredentials : true,
      data:{
        'user':mPK.value
      },
      headers: {
        Authorization: `Token ${token.value}`, // 토큰 포함
      },
    })
    .then((response) => {
      is_liked.value = response.data;
      console.log(is_liked)
      visibleItems()
      console.log("성공")
      console.log(response)

    // store의 deposit 데이터 업데이트
      // bankId = deposit.find((b) => b.id === bankId);
      if (bankId) {
        bankId.is_liked = is_liked; // Django에서 반환된 새로운 상태
      }
    })
    .catch((err) => {
      const errorMessage = err.response?.data?.error || '로그인이 필요합니다.';
      alert(errorMessage)
      console.log(token.value)
      console.log(err)
      console.log("실패")
    })
  }
  const likeList=ref([])
  const visibleItems = function () {
    axios({
      method:'get',
      url: `${API_URL}/api/v1/api/bank/liked/`,
      withCredentials : true,
      headers: {
        Authorization: `Token ${token.value}`, // 토큰 포함
      },
    })
    .then((response)=> {
      likeList.value = response.data
      console.log(likeList.value)
    })
    .catch((err) => {
      console.log(err)
    })
  }

  const coments = ref([])
  // 댓글 호출
  const commentsGet = function () {
    axios({
      method: 'get',
      url: `${API_URL}/api/v1/comments/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
        coments.value = response.data
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 댓글 작성
  const commentsCreate = function (product_pk, data) {
    axios({
      method: 'post',
      url: `${API_URL}/api/v1/${product_pk}/comments/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
      data: data,
    })
      .then((response) => {
        console.log(response.data)
        coments.value = response.data
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 댓글 삭제
  const commentsDelete = function (comment_pk) {
    axios({
      method: 'post',
      url: `${API_URL}/api/v1/comments/${comment_pk}/delete/`,
      withCredentials:true,
      headers: {
        Authorization: `Token ${token.value}`
      },
    })
      .then((response) => {
        console.log(response.data)
        coments.value = response.data
      })
      .catch((err) => {
        console.log(err)
      })
  }
  

  const userMessage = ref('');
  const botReply = ref('');
  const isLoading = ref(false);

  const sendMessage = function () {
    if (!userMessage.value.trim()) return;
  
    // 사용자 메시지 전송 전에 로딩 상태로 설정
    isLoading.value = true;
    botReply.value = ''; // 이전 답변 초기화
  
    axios({
      method: 'post',
      url: `${API_URL}/chatbot/api/chat/`,  // Django 서버의 API URL
      data: {
        message: userMessage.value,
      },
      withCredentials:true,
      headers: {
        // 인증 토큰이 필요하면 이 부분에 추가
        Authorization: `Token ${token.value}`,  // 예시: Token 인증 헤더 (필요에 따라 수정)
      },
    })
      .then((response) => {
        // 성공적으로 답변을 받으면, 챗봇의 답변을 상태에 저장
        botReply.value = response.data.message;
      })
      .catch((error) => {
        // 오류 처리 (예: 서버가 다운되었을 때)
        botReply.value = 'Sorry, something went wrong. Please try again.';
        console.log(error);
      })
      .finally(() => {
        // 로딩 상태 해제
        isLoading.value = false;
      });
  };

  // 환율정보 받기
  const exchangelist = ref([])
  const getexchange = function () {
    axios({
      method: 'get',
      url: `${API_URL}/exchange/exchangedata/`,
    })
      .then((response) => {
        exchangelist.value = response.data
        console.log(exchangelist.value)
      })
      .catch((err) => {
        console.log(err)
      })
  }
  
  return { companyList, companyListOption, 
    integrationProducts, integrationProductOptions, 
    API_URL, surveyData,
    getCompany, getIntegration, getcompany2,
    getdeposit, getsaving, getmortgageLoan, getrentHouseLoan, delete_data,
    getSurveyData, updateSurveyData,
    signUp, logIn, token, isLogin, logOut,getAnnouncementData, 
    announcements, mPK, createSurvey, selected, checkType, bankId, toggleLike,
    Uname, Uemail, visibleItems, likeList, is_liked, isAdmin, 
    commentsCreate, commentsDelete, coments, commentsGet,
    userMessage, botReply, isLoading, sendMessage,
    getexchange, exchangelist
   }
}, { persist: true })
