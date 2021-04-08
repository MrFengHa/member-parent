package com.home.controller;

import com.home.config.OSSProperties;
import com.home.constant.CrowdConstant;
import com.home.entity.vo.ProjectVO;
import com.home.entity.vo.ReturnVO;
import com.home.util.CrowdUtil;
import com.home.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/4/7 9:41
 */
@Controller
public class ProjectConsumerController {
    @Autowired
    private OSSProperties ossProperties;

    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession httpSession) {
        try {
            //1.从session域中读取之前缓存的ProjectVO对象
            ProjectVO projectVO = (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            //判断project是否为空
            if (projectVO == null) {
                return ResultEntity.error(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            //3.从projectVO对象中存储回报集合的信息
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            //4.判断returnVOList集合是否有效
            if (returnVOList == null || returnVOList.size() == 0) {
                //5.创建集合对象 returnVOList进行初始化
                returnVOList = new ArrayList<ReturnVO>();
                //6.为了让以后能正常使用这个集合 设置到projectVO对象中
                projectVO.setReturnVOList(returnVOList);
            }
            //7.将收集了表单数据的returnVO对象存入集合
            returnVOList.add(returnVO);
            //8.把数据有变化的ProjectVO对象重新存入Session域，以确保新的数据最终存入Redis
            httpSession.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            //9.所有操作成功就返回成功
            return ResultEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error(e.getMessage());
        }
    }

    /**
     * 上传单个文件
     *
     * @param returnPicture
     * @return
     */
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {

        //1.执行文件上传
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename()
        );

        //2.返回上传结果
        return uploadReturnPicResultEntity;
    }

    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(
            //用于接收除了上传图片之外的普通数据
            ProjectVO projectVO,
            //接收我们上传的头图
            MultipartFile headerPicture,
            //接收上传的详情的图片
            List<MultipartFile> detailPictureList,
            //用来将收集一部分数据的ProjectVO对象存入Session域
            HttpSession httpSession,
            //用来操作失败后返回上一个表单页面携带提示消息
            ModelMap modelMap
    ) throws IOException {

        //1.头图的上传  获取当前对象是否为空
        boolean headerPictureEmpty = headerPicture.isEmpty();
        if (headerPictureEmpty) {
            //如果上传失败返回表单页面显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_HEADER_PIC_EMPTY);
            return "project-launch";
        }

        //如果headerPictureEmpty不为空，执行上传到OSS
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        String result = uploadHeaderPicResultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {
            //4.从返回的数据中获取图片路径
            String headerPicturePath = uploadHeaderPicResultEntity.getData();

            //5.存入到ProjectVO中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            //如果上传失败返回表单页面显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }

        //创建一个用来存放详情图片的集合
        List<String> detailPicturePathList = new ArrayList<String>();


        if (detailPictureList == null || detailPictureList.size() == 0) {
            //如果上传失败返回表单页面显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
            return "project-launch";
        }

        //4.遍历detailPictureList集合
        for (MultipartFile detailFile : detailPictureList) {
            if (detailFile.isEmpty()) {
                //如果上传失败返回表单页面显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
            //5.判断detailPicture是否为空
            if (!detailFile.isEmpty()) {
                //6.执行上传
                ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(
                        ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getAccessKeySecret(),
                        detailFile.getInputStream(),
                        ossProperties.getBucketName(),
                        ossProperties.getBucketDomain(),
                        detailFile.getOriginalFilename()
                );
                //7.检查上传的结果
                String detailUploadResult = detailUploadResultEntity.getResult();
                if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
                    String detailPicturePath = detailUploadResultEntity.getData();
                    //收集刚刚上传图片的访问路径
                    detailPicturePathList.add(detailPicturePath);
                }
            }
        }
        //9.将存放详情图片路径的集合存入到ProjectVO中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        //10.将ProjectVO对象存入Session对象
        httpSession.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        //11.以完整的访问路径前往下一个收集回报信息页面
        return "project-return";
    }
}
