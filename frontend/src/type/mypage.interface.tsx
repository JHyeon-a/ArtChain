// 기업 마이페이지 response
export interface BusinessMyPageResponse {
  memberComMypageDto: BusinessNameResponse;
  fundingComShareList: BusinessMyPageFunding[];
}

export interface BusinessNameResponse {
  id: number;
  name: string;
}

export interface BusinessMyPageFunding {
  id: number;
  name: string;
  progressStatus: string;
  poster: string;
  nowCoinCount: number;
  goalCoinCount: number;
  recruitEnd: string;
  status: string;
  share: number;
}
export interface GetMyPieceDropDown {
  fundingId: number;
  fundingName: string;
  poster: string;
}

export interface GetMyPieceListParams {
  fundingId: number;
  filterFlag: string;
  page: number;
  size: number;
}

export interface GetMyPieceListResponse {
  transactionType: string;
  transactionHash: string;
  id: number;
  pieceCount: number;
  coinCount: number;
  status: string;
  tradeFlag: string;
  createdAt: string;
}

export interface GetMyInvestmentHistoryResponse {
  myIntegratedList: GetMyIntegratedList[];
}

export interface GetMyIntegratedList {
  fundingId: number;
  fundingProgressStatus: string;
  fundingTitle: string;
  fundingPoster: string;
  pieceCount: number;
  pieceUnitPrice: number;
  shareholdingRatio: number;
  settlementDate: string;
  settlementCoin: number;
  returnRate: number;
}

export interface GetMyInvestmentHistoryParams {
  status: string;
}

export interface GetMyPieceCountResponse {
  pieceOwnerList: GetMyPieceCountList[];
}

export interface GetMyPieceCountList {
  id: number;
  memberId: number;
  memberName: string;
  fundingId: number;
  fundingTitle: string;
  pieceCount: number;
}
