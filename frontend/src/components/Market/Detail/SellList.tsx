import { Box, Center, Flex, Image, Text } from "@chakra-ui/react";
import puzzle from "../../../assets/puzzle.svg";
import { getMarketSellingDisplayListInterface } from "../../../type/market.interface";

export default function SellList(params: getMarketSellingDisplayListInterface) {
  return (
    <>
      <Center>
        <Box p={"1rem"}>
          <Box
            w={"160px"}
            h={"175px"}
            border={"1px"}
            borderColor={"gray.300"}
            borderRadius={"0.5rem"}
          >
            <Center
              w={"160px"}
              borderRadius={"0.5rem"}
              bgColor={"blue.100"}
              h={"70px"}
            >
              <Flex>
                <Image boxSize={"1.2rem"} src={puzzle}></Image>
                <Text
                  as={"b"}
                  color={"black.100"}
                  fontSize={"0.9rem"}
                  ml={"0.2rem"}
                >
                  {params.pieceCount} 조각
                </Text>
              </Flex>
            </Center>
            <Flex>
              <Flex
                minW={"75px"}
                maxW={"180px"}
                direction={"column"}
                p={"0.5rem"}
              >
                <Text fontSize={"0.7rem"}>총 가격</Text>
                <Text fontSize={"0.7rem"} mt={"0.1rem"}>
                  조각 당 가격
                </Text>
                <Text fontSize={"0.7rem"} mt={"0.1rem"}>
                  판매자 주소
                </Text>
              </Flex>
              <Flex
                minW={"75px"}
                maxW={"180px"}
                direction={"column"}
                p={"0.5rem"}
              >
                <Text as={"b"} fontSize={"0.7rem"}>
                  {params.totalCoin} 아트
                </Text>
                <Text as={"b"} fontSize={"0.7rem"} mt={"0.1rem"}>
                  {params.coinPerPiece} 아트
                </Text>
                <Text as={"b"} fontSize={"0.7rem"} mt={"0.1rem"}>
                  {params.sellerAddress.substring(0, 6)}...
                </Text>
              </Flex>
            </Flex>
            <Flex justifyContent={"center"}>
              <Box
                w={"140px"}
                border={"1px"}
                borderColor={"gray.300"}
                borderRadius={"0.5rem"}
                textAlign={"center"}
                bgColor={"blue.300"}
              >
                <Text as={"b"} fontSize={"0.8rem"} color={"white"}>
                  구매
                </Text>
              </Box>
            </Flex>
          </Box>
        </Box>
      </Center>
    </>
  );
}
