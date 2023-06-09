import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

import memberStore from "@/store/modules/memberStore";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    dataByDay: [],  //나의여행계획에서 사용할 state
  },
  getters: {},
  mutations: {
    //나의여행계획에서 날짜별 등록하기 누르면 vuex.store에 추가된다
    updateDataByDay(state, payload) {
      state.dataByDay.push(payload); // 데이터를 배열에 push
    },
    //나의여행계획에서 리셋되게 하는 뮤테이션 
    resetDataByDay(state) {
      state.dataByDay = [];
    },
  },
  actions: {},
  modules: {
    memberStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
