<template>
  <b-modal
    v-model="showAttractionDetailModal"
    id="signUpModal"
    :title="attractionData.title"
    :title-class="'my-custom-title'"
    hide-footer
  >
    <div class="modal-body" style="padding: 1.5rem; padding-top: 5px">
      <!-- <div class="row mb-1 d-flex justify-content-center modal-title mr-0" style="margin-right: 0px;margin-left: 0px;">
        {{ attractionData.title }}
      </div> -->
      <div class="row mb-3 text-center">
        <img :src="imageUrl" alt="이미지 없음" class="modal-image" />
      </div>

      <div class="row mb-1">
        {{ attractionData.attractionOverview }}
      </div>
    </div>
    <div class="modal-footer" style="align: center">
      <b-button
        id="btn-join"
        type="button"
        variant="primary"
        @click="routeToBoardWrite"
        >동행모으기</b-button
      >
      <b-button type="button" variant="danger" @click="routeToBoardList"
        >동행찾기</b-button
      >
    </div>
  </b-modal>
</template>

<script>
import axios from "axios";
export default {
  name: "AttractionDetailModal",
  data() {
    return {
      showAttractionDetailModal: false,
      selectContentId: null,
      attractionData: {
        title: null,
        attractionOverview: null,
        imgUrl: null,
      },
      imageUrl: null,
    };
  },
  methods: {
    show(selectContentId) {
      this.selectContentId = selectContentId;
      this.showAttractionDetailModal = true;
      this.getAttractionDescription();
    },
    getAttractionDescription() {
      axios
        .get(
          `http://127.0.0.1:9018/navigator/attrDescription?contentId=${this.selectContentId}`
        )
        .then((response) => {
          this.attractionData.title = response.data.title;
          this.attractionData.attractionOverview = response.data.overview;
          this.attractionData.imgUrl = response.data.first_image;
          this.imageUrl = this.attractionData.imgUrl; // imageUrl에 imgUrl 할당
          if (!this.imageUrl) {
            // 대체 이미지 URL 설정
            this.imageUrl =
              "https://github-production-user-asset-6210df.s3.amazonaws.com/77240765/239945602-91ba30bf-d797-4897-88d8-1785d6bfdadc.gif";
          }
        });
    },
    routeToBoardWrite() {
      //title을 가지고 글쓰기모달로 가서 장소에 이름을 박는다.
      this.$router.push({
        path: "/board/api/list",
        query: { place: this.attractionData.title },
      });
    },
    routeToBoardList() {
      //리스트에서 검색기능구현되면 검색조건으로 파라미터를 들고 나간다
      // console.log("동행찾기 버튼 클릭");
      this.$router.push({
        path: "/board/api/list",
        query: { searchKeyword: this.attractionData.title },
      });
    },
  },
};
</script>

<style>
.modal-image {
  max-width: 100%;
  max-height: 100%;
}

.modal-title {
  font-weight: bolder;
  font-size: xx-large;
}

.my-custom-title {
  padding-bottom: 5px;
}
</style>
