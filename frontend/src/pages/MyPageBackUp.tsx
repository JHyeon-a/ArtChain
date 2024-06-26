import { Box, Button, Text, Image, Flex, Center } from "@chakra-ui/react";

import wallet from "../assets/wallet.svg";
import copy from "../assets/copy.svg";
import puzzle from "../assets/puzzle.svg";
import rightarrow from "../assets/rightarrow.svg";

import BackIcon from "../assets/back-icon.svg";
import { useNavigate, Link } from "react-router-dom";
import DoughnutChart from "../components/Mypage/DoughnutChart";

export default function MyPageBackUp() {
  const num = 100;
  const navigate = useNavigate();
  const handleBack = () => {
    navigate(-1); //뒤로가기
  };

  const handleCharge = () => {
    navigate("/charge");
  };

  const handleExchange = () => {
    navigate("/exchange");
  };

  return (
    <>
      <Box
        bgGradient={"linear(to-r, #16d9e3 0%, #30c7ec 15%, #46aef7 80%)"}
        minW={"360px"}
        h={"380px"}
        borderRadius={"1rem"}
        boxShadow={"xl"}
      >
        <Box height={50} pl={30} display="flex" alignItems={"center"}>
          <Button variant="unstyled" onClick={handleBack}>
            <Image src={BackIcon} />
          </Button>
        </Box>
        <Box>
          {/* <Flex mt={"0.5rem"}>
            <Text as={"b"} fontSize={"1.7rem"} ml={"2rem"}>
              김지은님
            </Text>
            {userInfo.isLogin ? (
              <Image
                boxSize={"1rem"}
                src={settings}
                ml={"0.5rem"}
                mt={"0.8rem"}
                onClick={() =>
                  LogoutAxios()
                    .then(() => clearFunction())
                    .catch(() =>
                      toast({
                        duration: 2000,
                        isClosable: true,
                        position: "top",
                        render: () => (
                          <Flex
                            color="white"
                            mt={"50px"}
                            bg="#C70000"
                            p={"1rem"}
                            borderRadius={"0.7rem"}
                            alignItems={"center"}
                          >
                            <CheckCircleIcon
                              boxSize={5}
                              color={"white"}
                              ml={"0.5rem"}
                            />
                            <Center ml={"1rem"}>
                              <Text as={"b"}>로그아웃 실패</Text>
                            </Center>
                          </Flex>
                        ),
                      })
                    )
                }
              />
            ) : null}
          </Flex> */}
          <Flex ml={"1.5rem"}>
            <Image boxSize={"1rem"} src={wallet} ml={"0.5rem"} mt={"0.8rem"} />
            <Text fontSize={"1rem"} ml={"0.8rem"} mt={"0.7rem"}>
              0x67F07AFa...
            </Text>
            <Image boxSize={"1rem"} src={copy} ml={"0.3rem"} mt={"0.9rem"} />
          </Flex>
        </Box>
        <Center h={"150px"}>
          <Text as={"b"} fontSize={"2.5rem"}>
            10,000
          </Text>
          <Text as={"b"} fontSize={"2.5rem"} ml={"1rem"}>
            아트
          </Text>
        </Center>
        <Flex justifyContent={"center"}>
          <Box
            minW={"140px"}
            py={"0.7rem"}
            rounded={"0.7rem"}
            mt={"0.5rem"}
            fontSize={"sm"}
            bgColor={"blue.300"}
            onClick={handleCharge}
          >
            <Center color={"white.100"}>충전하기</Center>
          </Box>
          <Box
            minW={"140px"}
            py={"0.7rem"}
            rounded={"0.7rem"}
            mt={"0.5rem"}
            fontSize={"sm"}
            bgColor={"white.100"}
            ml={"2rem"}
            onClick={handleExchange}
          >
            <Center color={"blue.400"}>환전하기</Center>
          </Box>
        </Flex>
      </Box>
      <Flex
        mt={"1rem"}
        p={"1rem"}
        ml={"1rem"}
        justifyContent={"space-between"}
        minW={"360px"}
        as={Link}
        to={"/mypiece/1"}
      >
        <Flex>
          <Image boxSize={"2.7rem"} src={puzzle}></Image>
          <Text as={"b"} color={"black.100"} fontSize={"2rem"} ml={"1rem"}>
            나의 조각
          </Text>
        </Flex>

        <Image boxSize={"2.7rem"} src={rightarrow}></Image>
      </Flex>
      <Box ml={"2rem"}>
        <Box mt={"2.5rem"} h={"50px"}>
          <Text as={"b"} color={"black.100"} fontSize={"2rem"}>
            투자 요약
          </Text>
        </Box>
        <Flex direction={"column"} mt={"0.5rem"}>
          <Text as={"b"} fontSize={"1.2rem"}>
            전체 투자 횟수 : {num}회
          </Text>
          <Flex mt={"0.3rem"}>
            <Box
              w={"25px"}
              h={"25px"}
              borderRadius={"5px"}
              bgColor={"blue.100"}
            />
            <Text fontSize={"1.2rem"} ml={"0.5rem"}>
              모집 중 : {num}회
            </Text>
          </Flex>
          <Flex mt={"0.3rem"}>
            <Box
              w={"25px"}
              h={"25px"}
              borderRadius={"5px"}
              bgColor={"blue.200"}
            />
            <Text fontSize={"1.2rem"} ml={"0.5rem"}>
              정산 대기 : {num}회
            </Text>
          </Flex>
          <Flex mt={"0.3rem"}>
            <Box
              w={"25px"}
              h={"25px"}
              borderRadius={"5px"}
              bgColor={"blue.300"}
            />
            <Text fontSize={"1.2rem"} ml={"0.5rem"}>
              정산 완료 : {num}회
            </Text>
          </Flex>
          <Flex mt={"0.3rem"}>
            <Box
              w={"25px"}
              h={"25px"}
              borderRadius={"5px"}
              bgColor={"gray.300"}
            />
            <Text fontSize={"1.2rem"} ml={"0.5rem"}>
              모집 실패 : {num}회
            </Text>
          </Flex>
        </Flex>
      </Box>
      <Flex justifyContent={"center"} p={"4rem"}>
        <DoughnutChart />
      </Flex>
    </>
  );
}
