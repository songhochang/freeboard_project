<template>
  <header class="main_header">
    <div class="main_div1">
      <!-- 현재 페이지에 따라 버튼의 동작을 변경 -->
      <button class="btn1" @click="navigate">
        {{ currentRoute === '/' ? 'FreeBoard' : 'Home' }}
      </button>
    </div>
  </header>
  <RouterView />
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import { computed } from 'vue';

// vue-router를 이용한 라우터 기능 사용
const router = useRouter();
const route = useRoute();

// 현재 경로를 반응형으로 관리
const currentRoute = computed(() => route.path);

// 버튼 클릭 시 경로 이동 함수
const navigate = () => {
  if (currentRoute.value === '/') {
    router.push('/freeboard'); // 홈에서 FreeBoard로 이동
  } else {
    router.push('/'); // FreeBoard에서 홈으로 이동
  }
};
</script>

<style scoped>
.main_header {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #f5f5f5;
  height: 150px;
}

.main_div1 {
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn1 {
  width: 120px; /* 버튼의 고정 너비 설정 */
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center; /* 텍스트를 가운데 정렬 */
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #0056b3;
  }
}

</style>
