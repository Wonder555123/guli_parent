package com.atguigu.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {

    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("视频ID");
        return client.getAcsResponse(request);
    }

    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("视频ID");
        return client.getAcsResponse(request);
    }

    public static void main(String[] args) throws ClientException {

            getPlay();

    }

    public static void getPlayUrl() throws ClientException {
//        1-根据视频ID获取视频播放地址
//        创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tJyxde5CebpqPz3Pm49",
                "rwcSDTWqpRc475dFyqvAsOg0SBBExQ");
//        创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
//        向request对象里面设置视频id
//      未转码加密视频   00a8248f8add47afb638a65c02ece07d
        request.setVideoId("00a8248f8add47afb638a65c02ece07d");
        response = client.getAcsResponse(request);
//        调用初始华对象里面的方法传递request
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    public static void getPlay() throws ClientException {
        System.out.println("*********************************************");
        //        1-根据视频ID获取视频播放地址
//        创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tJyxde5CebpqPz3Pm49",
                "rwcSDTWqpRc475dFyqvAsOg0SBBExQ");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//        5a33204e1f08487a8395b3235c134ce2
        request.setVideoId("5a33204e1f08487a8395b3235c134ce2");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }

}
