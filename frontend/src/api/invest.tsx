import {
  GetFunddingListParams,
  GetFunddingListResponse,
  GetFundingParams,
  GetFundingResponse,
  GetNoticeParams,
  Notice,
  PostFundingParams,
  PostFundingResponse,
  PostFundingNoticeRequest,
  PutFundingNoticeRequest,
  DeleteFundingNoticeRequest,
  FundingAllowRequest,
  FundingStatusRequest,
} from "../type/invest.interface";
import { makeQuerystring } from "../utils/ApiUtils";
import { localAxios } from "./https";

//투자 리스트 보기
export const getFunddingList = async (
  params: GetFunddingListParams
): Promise<GetFunddingListResponse | string> => {
  const { category, status, allowStat, page, size } = params;
  const url = `/funding/list${makeQuerystring({
    category,
    status,
    allowStat,
    page,
    size,
  })}`;
  console.log(url);
  const response = await localAxios.get(url);

  if (response.data.data && response.data.data.fundingList) {
    return response.data.data;
  } else {
    return response.data.message;
  }
};

//투자 상세보기
export const getFundding = async (
  params: GetFundingParams
): Promise<GetFundingResponse> => {
  const { fundingId } = params;
  const url = `/funding/${fundingId}`;
  console.log(url);

  const response = await localAxios.get(url);

  return response.data.data;
};

//투자 공지사항 상세보기
export const getNotice = async (params: GetNoticeParams): Promise<Notice> => {
  const { fundingId, fundingNoticeId } = params;
  const url = `/funding/${fundingId}/notice/${fundingNoticeId}`;

  const response = await localAxios.get(url);
  return response.data.data;
};

//투자하기 (개인)
export const PostInvest = async (
  params: PostFundingParams
): Promise<PostFundingResponse> => {
  const { fundingId, fundingRequest } = params;
  const url = `/funding/${fundingId}/invest`;

  const response = await localAxios.post(url, fundingRequest);

  return response.data.data.id;
};

// 펀딩 공지사항 작성(기업)
export const PostFundingNotice = async (
  params: PostFundingNoticeRequest
): Promise<PostFundingNoticeRequest> => {
  const { fundingId, notice } = params;

  const url = `/funding/${fundingId}/notice`;

  const response = await localAxios.post(url, notice);

  return response.data;
};

// 펀딩 공지사항 수정(기업)
export const PutFundingNotice = async (
  params: PutFundingNoticeRequest
): Promise<PutFundingNoticeRequest> => {
  const { fundingId, fundingNoticeId, notice } = params;

  const url = `/funding/${fundingId}/notice/${fundingNoticeId}`;

  const response = await localAxios.put(url, notice);

  return response.data;
};

// 펀딩 공지사항 삭제(기업)
export const DeleteFundingNotice = async (
  params: DeleteFundingNoticeRequest
): Promise<DeleteFundingNoticeRequest> => {
  const { fundingId, fundingNoticeId } = params;

  const url = `/funding/${fundingId}/notice/${fundingNoticeId}`;

  const response = await localAxios.delete(url);

  return response.data;
};

// 펀딩 승인/거절(관리자)
export const PutFundingAllow = async (
  params: FundingAllowRequest
): Promise<FundingAllowRequest> => {
  const { fundingId, allowStatus } = params;

  const url = `/funding/${fundingId}/allow/${allowStatus}`;

  const response = await localAxios.put(url);

  return response.data;
};

// 펀딩 진행 상태 수정(관리자)
export const PutFundingStatus = async (
  params: FundingStatusRequest
): Promise<FundingStatusRequest> => {
  const { fundingId, progressStatus } = params;

  const url = `/funding/${fundingId}/progress-status/${progressStatus}`;

  const response = await localAxios.put(url);

  return response.data;
};
