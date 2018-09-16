// js code for primary interactive logic
// javascript modularity
// seckill.detail.init(params);
var seckill = {
        // Package the urls of sekill-related ajax
        URL: {
            now: function () {
                return '/seckill/time/now';
            }
        },
        // Validate phone number
        validatePhone: function (phone) {
            if (phone && phone.length == 11 && !isNaN(phone)) {
                return true;
            } else {
                return false;
            }
        },

        countdown: function (seckillId, nowTime, startTime, endTime) {
            var seckillBox = $('#seckill-box');
            // Time determination
            if (nowTime > endTime) {
                // seckill ends
                seckillBox.html('Seckill End!');
            } else if (nowTime < startTime) {
                // seckill hasn't started, bind countdown event
                var killTime = new Date(startTime + 1000);
                seckillBox.countdown(killTime, function (event) {
                    // Time format
                    var format = event.strftime('Seckill Countdown: %D days %H hours %M mins %S secs');
                    seckillBox.html(format);
                });
            } else {
            }
        },

        // Logic of detail page
        detail:
            {
                // Initialize the detail page
                init: function (params) {
                    // Phone number validation and login, time interaction
                    // Planning for the interactive processes
                    // Search phone number in cookie
                    var killPhone = $.cookie('killPhone');

                    if (!seckill.validatePhone(killPhone)) {
                        // Bind phone number
                        // Control output
                        var killPhoneModal = $('#killPhoneModal');
                        killPhoneModal.modal({
                            // Display prompt out layer
                            show: true,
                            backdrop: 'static', // Forbid closing the location
                            keyboard: false // Close keyboard event
                        });
                        $('#killPhoneBtn').click(function () {
                            var inputPhone = $('#killPhoneKey').val();
                            console.log('inputPhone=' + inputPhone); //TODO
                            if (seckill.validatePhone(inputPhone)) {
                                // write phone number to cookie
                                $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                                // Refresh page
                                window.location.reload();
                            } else {
                                $('#killPhoneMessage').hide().html('<label class="label label-danger">Invalid Phone Number!</label>').show(300);
                            }
                        });
                    }
                    // Have logged in
                    // Countdown interaction
                    var startTime = params['startTime'];
                    var endTime = params['endTime'];
                    var seckillId = params['seckillId'];
                    $.get(seckill.URL.now(), {}, function (result) {
                        if (result && result['success']) {
                            var nowTime = result['data'];
                            // Time determination, timing interaction
                            seckill.countdown(seckillId, nowTime, startTime, endTime);
                        } else {
                            console.log('result:' + result);
                        }
                    });
                }
            }
    }
;

