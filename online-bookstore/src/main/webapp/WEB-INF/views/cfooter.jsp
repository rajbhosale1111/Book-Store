<div class="footer">
	<div class="row">
		<div class="col-sm-3 p-4">
			<div class=" span_1_of_4" style="width: 100%">
				<h4>Information</h4>
				<ul>
					<li><a href="#">About Us</a></li>
					<li><a href="#">Customer Service</a></li>
					<li><a href="#"><span>Advanced Search</span></a></li>
					<li><a href="#">Orders and Returns</a></li>
					<li><a href="#"><span>Contact Us</span></a></li>
				</ul>
			</div>
		</div>
		<div class="col-sm-3 p-4">
			<div class=" span_1_of_4" style="width: 100%">
				<h4>Why buy from us</h4>
				<ul>
					<li><a href="about.html">About Us</a></li>
					<li><a href="faq.html">Customer Service</a></li>
					<li><a href="#">Privacy Policy</a></li>
					<li><a href="contact.html"><span>Site Map</span></a></li>
					<li><a href="preview-2.html"><span>Search Terms</span></a></li>
				</ul>
			</div>
		</div>
		<div class="col-sm-3 p-4">
			<div class=" span_1_of_4" style="width: 100%">
				<h4>My account</h4>
				<ul>
					<li><a href="contact.html">Sign In</a></li>
					<li><a href="index.html">View Cart</a></li>
					<li><a href="#">My Wishlist</a></li>
					<li><a href="#">Track My Order</a></li>
					<li><a href="faq.html">Help</a></li>
				</ul>
			</div>
		</div>
		<div class="col-sm-3 p-4">
			<div class=" span_1_of_4" style="width: 100%">
				<h4>Contact</h4>
				<ul>
					<li><span>+91-981-xxx-xxxx</span></li>
					<li><span>+91-999-xxx-xxxx</span></li>
				</ul>
			</div>
		</div>
	</div>
</div>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<script src="js/menu.js" type="text/javascript"></script>
<script src="js/jquery.flexslider.js" type="text/javascript"></script>
<script type="text/javascript">
                $(function () {                    
                    $('ul.dropdown-menu [data-toggle=dropdown]').on('click', function (event) {
                        //event.preventDefault();
                        event.stopPropagation();
                        $(this).parent().siblings().removeClass('open');
                        $(this).parent().toggleClass('open');
                    });
                });
                $(function(){
                    $('.flexslider').flexslider({
                        animation: "slide",
                        start: function (slider) {
                            $('body').removeClass('loading');
                        }
                    });
                });                    
            </script>

</body>
</html>