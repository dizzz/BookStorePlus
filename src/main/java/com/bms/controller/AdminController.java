
package com.bms.controller;
import com.bms.auth.BookValidator;
import com.bms.model.*;
import com.bms.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookValidator bookValidator;
    private List<Category>tags;
    private List<Publisher>publishers;
    @RequestMapping(value = {"/","home"})
    public String home(){
        return "admin/home";
    }
    @RequestMapping("/show")
    public String show(Model model){
        model.addAttribute("users",userService.queryAll());
        return "admin/show";
    }
    @RequestMapping("/usermanage")
    public String usermanage(Model model){
        model.addAttribute("users",userService.queryAll());
        return "admin/user/usermanage";
    }
    @RequestMapping("/deluser")
    public String deluser(HttpServletRequest request){
        userService.deleteUserById(request.getParameter("id"));
        return "redirect:/admin/usermanage";
    }
    @RequestMapping("/userdetail")
    public ModelAndView userdetail(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",userService.queryUserById(id));
        modelAndView.setViewName("admin/user/userdetail");
        return modelAndView;
    }
    @RequestMapping("/updateuserstate")
    public String updateuserstate(Integer userId,String act){
        if(userId != null && act != null){
            userService.updateUserState(userId,"enable".equals(act)?1:2);
        }
        return "redirect:/admin/usermanage";
    }

    ///BOOK////////////////////////////////////////////////////////////////////////////
    @RequestMapping("/bookmanage")
    public String bookmanage(Model model,Integer pageNum,String key){
        if(pageNum==null) pageNum=1;
        List<Book> list;
        if(key != null && key.length()!=0){
            list=bookService.queryBookByKey(key,pageNum,10  );
        }else {
            list = bookService.query(pageNum, 20);
        }
        model.addAttribute("page",new PageInfo<Book>(list));
        model.addAttribute("books",list);
        return "admin/book/bookmanage";
    }
    @RequestMapping(value = "/addbook",method = RequestMethod.GET)
    public String addbook(Model model){
        tags = bookService.queryAllCategories();
        publishers = bookService.queryAllPublishers();
        model.addAttribute("tags",tags);
        model.addAttribute("publishers",publishers);
        model.addAttribute("book",new Book());
        return "admin/book/addbook";
    }
    @RequestMapping(value = "/addbook",method = RequestMethod.POST)
    public String addbook(@ModelAttribute("book")Book book, BindingResult bindingResult, HttpServletRequest request,MultipartHttpServletRequest multiReq,Model model){
        bookValidator.validate(book,bindingResult);
        MultipartFile file = multiReq.getFile("cover");
        //TODO
//并没有上传文件
        if(bindingResult.hasErrors() || file == null){
            model.addAttribute("tags",tags);
            model.addAttribute("publishers",publishers);
            return "admin/book/addbook";
        }
        String uploadFilePath = file.getOriginalFilename();
        String uploadFileName = book.getISBN();
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) multiReq.getFile("cover").getInputStream();
            String filePath = request.getSession().getServletContext().getRealPath("WEB-INF/classes/static/image/");
            fos = new FileOutputStream(new File(filePath + uploadFileName
                    + ".")
                    + uploadFileSuffix.toLowerCase());
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bookService.addBook(book);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping(value = "/delbook")
    public String delbook(Integer id){
        bookService.delBook(id);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping(value = "/modifybook",method = RequestMethod.GET)
    public ModelAndView modifybook(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tags",bookService.queryAllCategories());
        modelAndView.addObject("publishers",bookService.queryAllPublishers());
        modelAndView.addObject("book",bookService.queryBookById(id));
        modelAndView.setViewName("/admin/book/modifybook");
        return modelAndView;
    }
    @RequestMapping(value = "/modifybook",method = RequestMethod.POST)
    public String modifybook(Book book){
        bookService.updateBook(book);
        return "redirect:/admin/bookmanage";
    }
    @RequestMapping("rating")
    public ModelAndView rating(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ratings",bookService.queryBookRatingByBookId(id));
        modelAndView.setViewName("/admin/book/rating");
        return modelAndView;
    }
    @RequestMapping("delrating")
    public String delrating(Integer ratingId,Integer bookId){
        bookService.delBookRatingById(ratingId);
        return "redirect:/admin/rating?id="+bookId;
    }
    ///Category//////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("categorymanage")
    public ModelAndView categorymanage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("tags",bookService.queryAllCategories());
        modelAndView.setViewName("/admin/category/categorymanage");
        return modelAndView;
    }
    @RequestMapping(value = "/addcategory",method = RequestMethod.GET)
    public String addcategory(Model model){
        model.addAttribute("category",new Category());
        return "admin/category/addcategory";
    }
    @RequestMapping(value = "/addcategory",method = RequestMethod.POST)
    public String addcategory(Category category,Model model){
        bookService.addCategory(category);
        return "redirect:/admin/categorymanage";
    }
    @RequestMapping(value = "/delcategory")
    public String delcategory(Integer id){
        bookService.delCategory(id);
        return "redirect:/admin/categorymanage";
    }
    @RequestMapping(value = "/modifycategory",method = RequestMethod.GET)
    public ModelAndView modifycategory(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category",bookService.queryCategoryById(id));
        modelAndView.setViewName("/admin/category/modifycategory");
        return modelAndView;
    }
    @RequestMapping(value = "/modifycategory",method = RequestMethod.POST)
    public String modifycategory(Category category){
        bookService.updateCategory(category);
        return "redirect:/admin/categorymanage";
    }
    ///Publisher//////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping("publishermanage")
    public ModelAndView publishermanage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("publishers",bookService.queryAllPublishers());
        modelAndView.setViewName("/admin/publisher/publishermanage");
        return modelAndView;
    }
    @RequestMapping(value = "/addpublisher",method = RequestMethod.GET)
    public String addpublisher(Model model){
        model.addAttribute("publisher",new Publisher());
        return "admin/publisher/addpublisher";
    }
    @RequestMapping(value = "/addpublisher",method = RequestMethod.POST)
    public String addpublisher(Publisher publisher,Model model){
        bookService.addPublisher(publisher);
        return "redirect:/admin/publishermanage";
    }
    @RequestMapping(value = "/delpublisher")
    public String delpublisher(Integer id){
        bookService.delPublisher(id);
        return "redirect:/admin/publishermanage";
    }
    @RequestMapping(value = "/modifypublisher",method = RequestMethod.GET)
    public ModelAndView modifypublisher(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("publisher",bookService.queryPublisherById(id));
        modelAndView.setViewName("/admin/publisher/modifypublisher");
        return modelAndView;
    }
    @RequestMapping(value = "/modifypublisher",method = RequestMethod.POST)
    public String modifypublisher(Publisher publisher){
        bookService.updatePublisher(publisher);
        return "redirect:/admin/publishermanage";
    }
    ///Order//////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("ordermanage")
    public ModelAndView ordermanage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orders",orderService.queryAllOrders());
        modelAndView.setViewName("/admin/order/ordermanage");
        return modelAndView;
    }
    @RequestMapping("orderdetail")
    public ModelAndView orderdetail(Integer id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("order",orderService.queryOrderById(id));
        modelAndView.addObject("items",orderService.queryOrderItemByOrderId(id));
        modelAndView.setViewName("/admin/order/orderdetail");
        return modelAndView;
    }
}
//TODO model函数统一
//TODO类别和出版社
